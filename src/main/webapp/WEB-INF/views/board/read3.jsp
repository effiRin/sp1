
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div>
    <div>
        <input type="text" name="replyText" value="샘플 댓글">
    </div>
    <div>
        <input type="text" name="replyer" value="testUser">
    </div>
    <div>
        <button class="addReplyBtn">댓글 추가</button>
    </div>
</div>

<h1> 댓글 수정 모달</h1>

<div>
    <div>
        <input type="text" name="modReplyText" >
    </div>
    <div>
        <input type="text" name="modReplyer" readonly>
    </div>
    <div>
        <button class="addReplyBtn">댓글 수정</button>
        <button class="removeReplyBtn">댓글 삭제</button>
    </div>
</div>

<ul class="replyUL">

</ul>

<ul class="pageUL">

</ul>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>

    // ---------------------------------------------------

    let initState = {
        bno : ${dto.bno},
        replyArr:[],
        replyCount:0,
        size: 10,
        pageNum:1
    }

    const replyUL = document.querySelector(".replyUL")
    const pageUL = document.querySelector(".pageUL")

    pageUL.addEventListener("click", (e) => {
        if(e.target.tagName != `LI`){
            return
        }

        const dataNum = parseInt(e.target.getAttribute("data-num"))
        replyService.setState({pageNum:dataNum})

    },false)

    document.querySelector(".addReplyBtn").addEventListener("click", (e) => {
        const replyObj = {
            bno : ${dto.bno},
            replyText: document.querySelector("input[name='replyText']").value,
            replyer : document.querySelector("input[name='replyer']").value
        }

        replyService.addServerReply(replyObj)

    },false)

    const modReplyTextInput = document.querySelector("input[name='modReplyText']")
    const modReplyerTextInput = document.querySelector("input[name='modReplyer']")
    const removeReplyBtn = document.querySelector(".removeReplyBtn")

    let targetLi

    replyUL.addEventListener("click", (e) => {

        if(!e.target.getAttribute("data-rno")){
            return;
        }
        const rno = parseInt(e.target.getAttribute("data-rno"))

        targetLi = e.target.closest("li")

        const replyObj = replyService.findReply(rno)
        modReplyTextInput.value = replyObj.replyText
        modReplyerTextInput.value = replyObj.replyer
        removeReplyBtn.setAttribute("data-rno", rno)

    },false)

    removeReplyBtn.addEventListener("click", (e) => {

        const rno = parseInt(e.target.getAttribute("data-rno"))
        replyService.removeServer(rno).then(result => {
            console.log(targetLi)
            targetLi.innerHTML = "DELETED"

        })

    },false)

    function render(obj){

        console.log("render......")  // render 돌고 있는지 확인
        console.dir(obj)  // 객체 상태 확인

        function printList(){
            const arr = obj.replyArr
            replyUL.innerHTML = arr.map(reply =>
                            `<li>\${reply.rno}
                                <div> \${reply.replyText}</div>
                                <button data-rno=\${reply.rno} class='modBtn'> 수정</button>
                            </li>`).join(" ")
        }

        function printPage(){

            const currentPage = obj.pageNum
            const size = obj.size

            let endPage = Math.ceil(currentPage/10) * 10
            const startPage = endPage - 9
            const prev  =startPage != 1
            endPage = obj.replyCount < endPage * obj.size? Math.ceil(obj.replyCount/obj.size) : endPage

            const next = obj.replyCount > endPage * obj.size

            console.log("startPage", startPage, "endPage", endPage, "currentPage", currentPage)

            let str = ''

            if(prev){
                str += `<li data-num=\${startPage-1}>이전</li>`
            }

            for(let i = startPage; i <= endPage; i++) {
                str += `<li data-num=\${i}>\${i}</li>`
            }

            if(next){
                str += `<li data-num=\${endPage+1}>다음</li>`
            }

            pageUL.innerHTML = str
        }

        printList()
        printPage()
    }

    // ---------axios 통신------------------------

    const replyService = (function(initState, callbackFn){

        let state = initState
        const callback = callbackFn

        const setState = (newState) => {
            state = {...state, ...newState}  // -> 전개 연산자 (펼쳐서 겹치는 건 통합하고, 안겹치는 건 추가)
            console.log(state)

            //newState 안에 replyCount 속성이 있다면 또는 pageNum이 있다면
            if(newState.replyCount || newState.pageNum){
                getServerList(newState)
            }

            callback(state)
        }

        async function getServerList(newState) {

            let pageNum

            if(newState.pageNum){
                pageNum = newState.pageNum
            }else{
                pageNum = Math.ceil(state.replyCount/state.size)
            }

//            const pageNum = Math.ceil(state.replyCount/state.size)

            const paramObj = {page:pageNum, size:state.size}

            const res = await axios.get(`/replies/list/\${state.bno}`, {params:paramObj})
            // axios로 데이터 가져오기

            console.log(res.data) // axios로 데이터 가져오면 우리에게 필요한 데이터는 res.data뿐

            state.pageNum = pageNum
            setState({replyArr : res.data})
        }

        async function addServerReply(replyObj){

            const res = await axios.post(`/replies/`, replyObj)

            const data = res.data

            console.log("addReplyResult", data)

            setState({replyCount : data.result})

        }

        function findReply(rno){
            return state.replyArr.find(reply => reply.rno === rno)
        }

        async function removeServer(rno){

            const res = await axios.delete(`/replies/\${rno}`)

            // 성공의 경우
            const result = res.data.result

            return result
        }

        return {setState, addServerReply, findReply, removeServer}
    })(initState,render)

    replyService.setState({replyCount:${dto.replyCount}}, render)
//    replyService.setState({pageNum:11}, render)


</script>

</body>
</html>
