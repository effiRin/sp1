<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Modify Page</h1>

${listDTO}


<form class="modForm" action="/board/modify/${dto.bno}" method="post">
    <input type="hidden" name="page" value="${listDTO.page}">
    <input type="hidden" name="size" value="${listDTO.size}">
    <input type="hidden" name="type" value="${listDTO.type}">
    <input type="hidden" name="keyword" value="${listDTO.keyword}">
<div>
    <div>
        <input type="text" name="bno" value="<c:out value="${dto.bno}"/>" readonly>
    </div>
    <div>
        <input type="text" name="title" value="<c:out value="${dto.title}"/>" >
    </div>
    <div>
        <textarea name="content" ><c:out value="${dto.content}"/></textarea>
    </div>
    <div>
        <input type="file" name="upload" multiple class="uploadFile">
        <button class="uploadBtn">UPLOAD</button>
    </div>

</div>
</form>



<div>
    <button class="listBtn">리스트</button>
    <button class="modPostBtn">수정</button>
    <button class="delPostBtn">삭제</button>
</div>

<style>
    .uploadResult {
        display: flex;

    }
    .uploadResult > div {
        margin: 3em;
        border: 1px solid red;
    }
</style>

<div class="uploadResult">
</div>

<form class="actionForm" action="/board/remove/${bno}" method="post">
</form>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>

    const uploadResult = document.querySelector(".uploadResult")

    function loadImages(){
        axios.get("/board/files/${dto.bno}").then(
            res => {
                const resultArr = res.data

                uploadResult.innerHTML += resultArr.map( ({uuid,thumbnail,link,fileName,savePath, img}) => `
                <div data-uuid='\${uuid}' data-img='\${img}'  data-filename='\${fileName}'  data-savepath='\${savePath}'>
                <img src='/view?fileName=\${thumbnail}'>
                <button data-link='\${link}' class="delBtn">x</button>
                \${fileName}</div>`).join(" ")
            }
        )
    }

    loadImages()

    uploadResult.addEventListener("click", (e) => {

        if(e.target.getAttribute("class").indexOf("delBtn") < 0){
            return
        }
        const btn = e.target
        const link = btn.getAttribute("data-link")

        deleteToServer(link).then(result => {
            btn.closest("div").remove()
        })

    }, false)

    //파일 업로드 버튼 이벤트 처리
    document.querySelector(".uploadBtn").addEventListener("click", (e) => {
        e.preventDefault()
        e.stopPropagation()

        //파일 업로드 처리
        const formObj = new FormData();

        const fileInput = document.querySelector(".uploadFile")

        console.log(fileInput.files)

        const files = fileInput.files

        for (let i = 0; i < files.length; i++) {
            console.log(files[i])
            formObj.append("files", files[i])
        }



        uploadToServer(formObj).then(resultArr => {

            uploadResult.innerHTML += resultArr.map( ({uuid,thumbnail,link,fileName,savePath, img}) => `
                <div data-uuid='\${uuid}' data-img='\${img}'  data-filename='\${fileName}'  data-savepath='\${savePath}'>
                <img src='/view?fileName=\${thumbnail}'>
                <button data-link='\${link}' class="delBtn">x</button>
                \${fileName}</div>`).join(" ")

        })

    }, false)

    async function deleteToServer(fileName){
        const options = {headers: { "Content-Type": "application/x-www-form-urlencoded"}}

        const res = await axios.post("/delete", "fileName="+fileName, options )

        console.log(res.data)

        return res.data
    }

    async function uploadToServer (formObj) {

        console.log("upload to server......")
        console.log(formObj)

        const response = await axios({
            method: 'post',
            url: '/upload1',
            data: formObj,
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        return response.data
    }

</script>

<script>

    function sQuery(expression){

        return document.querySelector(expression)
    }

    const bno = ${dto.bno}

    const actionForm = sQuery(".actionForm")

    document.querySelector(".listBtn").addEventListener("click",(e)=> {

        self.location = `/board/list${listDTO.link}`

    }, false)

    sQuery(".delPostBtn").addEventListener("click",(e)=> {

        actionForm.setAttribute("action", `/board/remove/${bno}`)
        actionForm.submit()

    }, false)

    document.querySelector(".modPostBtn").addEventListener("click",(e)=> {

        alert("EEEEEEEEEEEE")

        e.preventDefault()
        e.stopPropagation()

        const divArr = document.querySelectorAll(".uploadResult > div")

        let str = "";
        for(let i= 0;i < divArr.length; i++){
            const fileObj = divArr[i]

            if(i === 0){
                const mainImageLink = fileObj.querySelector("img").getAttribute("src")
                str += `<input type='hidden' name='mainImage' value='\${mainImageLink}'>`
            }

            const uuid = fileObj.getAttribute("data-uuid")
            const img = fileObj.getAttribute("data-img")
            const savePath = fileObj.getAttribute("data-savepath")
            const fileName = fileObj.getAttribute("data-filename")

            str += `<input type='hidden' name='uploads[\${i}].uuid' value='\${uuid}'>`
            str += `<input type='hidden' name='uploads[\${i}].img' value='\${img}'>`
            str += `<input type='hidden' name='uploads[\${i}].savePath' value='\${savePath}'>`
            str += `<input type='hidden' name='uploads[\${i}].fileName' value='\${fileName}'>`
        }//for

        const actionForm = document.querySelector(".modForm")
        actionForm.innerHTML += str

        actionForm.submit()

    }, false)

</script>

</body>
</html>
