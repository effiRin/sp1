<%--
  Created by IntelliJ IDEA.
  User: cooki
  Date: 2022-04-13
  Time: 오후 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

  <h1>Register Page</h1>
<form class="actionForm" action="/board/register" method="post">
    <input type="text" name="title" value="AAA">
    <input type="text" name="content" value="BBB">
    <input type="text" name="writer" value="user00">
    <button class="formBtn">CLICK</button>
    <div class="inputHiddens">

    </div>
</form>

<h2>파일 업로드 테스트</h2>
<form action="/upload1" method="post" enctype="multipart/form-data">
    <input type="file" name="files" multiple>
    <button>Upload</button>
</form>

<div>
    <h2>Ajax Upload</h2>
    <div class="uploadInputDiv">
    <input type="file" name="upload" multiple class="uploadFile">
    </div>
    <button class="uploadBtn">UPLOAD</button>
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


<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>

    const uploadResult = document.querySelector(".uploadResult")

    const cloneInput = document.querySelector(".uploadFile").cloneNode()

    document.querySelector(".formBtn").addEventListener("click",(e) => {
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

        document.querySelector(".inputHiddens").innerHTML = str;

        const actionForm = document.querySelector(".actionForm")

        actionForm.submit()


    },false)


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

    document.querySelector(".uploadBtn").addEventListener("click",(e)=> {

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

            fileInput.remove()
            document.querySelector(".uploadInputDiv").appendChild(cloneInput)

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

</body>
</html>
