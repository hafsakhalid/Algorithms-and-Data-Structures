package assignment2;

public class Shelf {
 
 protected int height;
 protected int availableLength;
 protected int totalLength;
 protected Box firstBox;
 protected Box lastBox;

 public Shelf(int height, int totalLength){
  this.height = height;
  this.availableLength = totalLength;
  this.totalLength = totalLength;
  this.firstBox = null;
  this.lastBox = null;
 }
 
 protected void clear(){
  availableLength = totalLength;
  firstBox = null;
  lastBox = null;
 }
 
 public String print(){
  String result = "( " + height + " - " + availableLength + " ) : ";
  Box b = firstBox;
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n";
  return result;
 }
 
 /**
  * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
  * @param b
  */
 public void addBox(Box b){
  Box newBox = b;
  if(this.lastBox == null){
    this.lastBox = this.firstBox = newBox;
  } else {
    lastBox.next = newBox;
    newBox.previous = lastBox;
    lastBox = newBox;
  }
  this.availableLength = availableLength - newBox.length;
 }
 
 /**
  * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
  * If not, do not do anything to the Shelf and return null.
  * @param identifier
  * @return
  */
 public Box removeBox(String identifier){
   //initilize two objects with the value of lastBox 
   Box box = lastBox;
   Box temp = lastBox;
   while(box != null){
     String string = box.id;
     //check to see if the boxID is the same as the identifier we're looking for,if it is that is the box we want.
     if(string.equals(identifier)){
       //Because we are going to remove this box, we have to change the available length
       this.availableLength = availableLength + box.length;
       temp = box;
       //this block runs only if there is only one box on the shelf. 
       if(box == lastBox && box == firstBox){ 
         box.next = null;
         box.previous = null;
         firstBox = lastBox = null;
         return temp;
       }
       //this block runs only if the box we're removing is the lastBox.
       else if(box == lastBox){
         lastBox = box.previous;
         lastBox.next = null;
         box.next = null;
         box.previous = null;
         return temp;
       }
       //this block runs only if the box we're removing is the firstBox
       else if (box == firstBox){
         firstBox = box.next;
         firstBox.previous = null;
         box.next = null;
         box.previous = null;
         return temp;
       } 
       //this block runs only if the box is somewhere in the middle of the shelf. 
       //Note: in this case, there are at least 3 boxes on the shelf.
       else{
         Box box1 = box.previous;
         Box box2 = box.next;
         box1.next = box2;
         box2.previous = box1;
         return temp;
       }
     } 
     //in case the boxID is not the same as the identifier input, we now check the previous box.
     else {
       box = box.previous;
       }
   }
   //in the case that we do not find any box matching the identifier, return a null value. 
   if (box == null){
     return null;
   }
   return null;
 }
}
