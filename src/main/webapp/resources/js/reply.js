
const replyService = (function(){

    let replyCountFn;

    const setReplyCount = function(fn){
        replyCountFn = fn
    }

    const addReply = async function (replyObj, size, callback){

        console.log("addReply............")

        const res = await axios.post("/replies/", replyObj)

        const replyCount = parseInt(res.data.result)

        console.log("REPLY JS ")
        console.log(replyCountFn)
        replyCountFn(replyCount)

        const bno = replyObj.bno
        const page = Math.ceil(replyCount/size)


        callback({bno,page,size})

    }

    const getList = async function({bno,page,size}, callback){


        console.log("getList.............", bno, page, size)

        const parameter = {page:page||1, size:size||10}

        const res = await axios.get(`/replies/list/${bno}`, {params: parameter })
        //console.log(res.data)
        callback(res.data)
    }

    return {addReply, getList, setReplyCount}

})()

const qs = function(str){
    return document.querySelector(str)
}

const qsAddEvent = function(selector, type, callback , tagName){

    const target = document.querySelector(selector)

    if(!tagName){
        target.addEventListener(type, callback, false)
    }else {

        target.addEventListener(type,(e) => {

            const realTarget = e.target

            if(realTarget.tagName !== tagName.toUpperCase()){
                return
            }

            callback(e, realTarget)


        },false)

    }

}







