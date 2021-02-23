var answer = [[],[]];
function solution(nodeinfo) {
    for (let i = 0, len = nodeinfo.length; i < len; i++) {
        nodeinfo[i].name = i+1;
        nodeinfo[i].left = null;
        nodeinfo[i].right = null;
    }
    nodeinfo.sort((node1,node2)=>{
        if(node2[1] == node1[1])
            return node1[0] - node2[0];
        return node2[1] - node1[1];
    })
    
    // 이진트리
    
    let tree = nodeinfo[0];
    for (let i = 1, len = nodeinfo.length; i < len; i++) {
        let insertNode = nodeinfo[i];
        let rootNode = tree;
        while(true){
            if(insertNode[0]<rootNode[0]){
                if(!rootNode.left){
                    rootNode.left = insertNode;
                    break;
                }
                else
                    rootNode = rootNode.left;
            }else if(insertNode[0]>rootNode[0]){
                if(!rootNode.right){
                    rootNode.right = insertNode;
                    break;
                }
                else   
                    rootNode = rootNode.right;
            }
        }
    }
    console.log(tree);
    
    preOrder(tree);
    postOrder(tree);
    return answer;
}
function preOrder(root){
    if(root){
        answer[0].push(root.name);
        preOrder(root.left);
        preOrder(root.right);
    }
}
function postOrder(root){
    if(root){
        postOrder(root.left);
        postOrder(root.right);
        answer[1].push(root.name);
    }
}
console.log(solution([[5,3],[11,5],[13,3],[3,5],[6,1],[1,3],[8,6],[7,2],[2,2]]));
