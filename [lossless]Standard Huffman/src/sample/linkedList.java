package sample;

public class linkedList {

    public linkedListNode head,
            tail;

    //default constructor
    public linkedList(){
        //tail=new linkedListNode();
        tail=null;
        //head=new linkedListNode();
        head=tail;
    }

    public boolean  isEmpty(){

        return(head == null);

    }
    public void display(){
        linkedListNode currListNode=head;

        while(currListNode!=null){
            currListNode.data.display();
            if(currListNode.nextNode==null){
                break;
            }else{
                currListNode=currListNode.nextNode;
            }
        }
    }//end of display() method

    public void     insertAtBeginning   (treeNode data){

        linkedListNode newlistNode=new linkedListNode(data);

        if(isEmpty()){
            tail=newlistNode;
            head=tail;
        }else{
            head.prevNode=newlistNode;
            newlistNode.nextNode=head;
            head=newlistNode;
        }

    }//end of insertAtBeginning() method
    public void     insertAtEnd         (treeNode data){

        linkedListNode newListNode=new linkedListNode(data);

        if(isEmpty()){
            insertAtBeginning(data);
        }else{
            tail.nextNode=newListNode;
            newListNode.prevNode=tail;
            tail=newListNode;
        }
    }//end of insertAtEnd() method
    public void     sortedInsertion     (treeNode data){

        linkedListNode newListNode=new linkedListNode(data);
        linkedListNode currListNode=head;

        if(isEmpty()){
            insertAtBeginning(data);
        }else{
            do{
                if (head==tail) {
                    if(data.symbolFreq <= currListNode.data.symbolFreq){insertAtEnd(data);}
                    else {insertAtBeginning(data);}
                    return;
                }else if(currListNode.nextNode==null){
                    insertAtEnd(data);
                    return;
                } else if(data.symbolFreq <= currListNode.data.symbolFreq) {
                    currListNode = currListNode.nextNode;
                }else{
                    break;
                }
            }while (data.symbolFreq <= currListNode.data.symbolFreq);

            if(currListNode==head){
                insertAtBeginning(data);
            }else{
                newListNode.prevNode=currListNode.prevNode;
                currListNode.prevNode.nextNode=newListNode;
                newListNode.nextNode=currListNode;
                currListNode.prevNode=newListNode;
            }
        }

    }//end of sortedInsertion() method

    public linkedListNode removeLastNode(){

        linkedListNode removedNode=tail;

        if(!isEmpty()){
            if(head==tail){
                tail=null;
                head=tail;
            }else{
                tail.prevNode.nextNode=tail.nextNode;
                tail=tail.prevNode;
            }
        }else{
            System.out.println("List is empty");
            return null;
        }
        return removedNode;
    }//end of removeLastNode() method


}//end of linkedList class
