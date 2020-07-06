package assignment2;

public class Warehouse{

 protected Shelf[] storage;
 protected int nbShelves;
 public Box toShip;
 public UrgentBox toShipUrgently;
 static String problem = "problem encountered while performing the operation";
 static String noProblem = "operation was successfully carried out";
 
 public Warehouse(int n, int[] heights, int[] lengths){
  this.nbShelves = n;
  this.storage = new Shelf[n];
  for (int i = 0; i < n; i++){
   this.storage[i]= new Shelf(heights[i], lengths[i]);
  }
  this.toShip = null;
  this.toShipUrgently = null;
 }
 
 public String printShipping(){
  Box b = toShip;
  String result = "not urgent : ";
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n" + "should be already gone : ";
  b = toShipUrgently;
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n";
  return result;
 }
 
  public String print(){
   String result = "";
  for (int i = 0; i < nbShelves; i++){
   result += i + "-th shelf " + storage[i].print();
  }
  return result;
 }
 
  public void clear(){
   toShip = null;
   toShipUrgently = null;
   for (int i = 0; i < nbShelves ; i++){
    storage[i].clear();
   }
  }
  
  /**
   * initiate the merge sort algorithm
   */
 public void sort(){
  mergeSort(0, nbShelves -1); 
 }
 
 /**
  * performs the induction step of the merge sort algorithm
  * @param start
  * @param end
  */
 protected void mergeSort(int start, int end){
   if (start < end){
     int middle = (start + end) / 2;
     mergeSort(start, middle);
     mergeSort(middle + 1, end);
     merge(start, middle, end);
   }
 }
 /**
  * performs the merge part of the merge sort algorithm
  * @param start
  * @param mid
  * @param end
  */
 protected void merge(int start, int mid, int end){
   Shelf[] tempArray = new Shelf[nbShelves];
   for (int i = start; i <= end; i++){
     tempArray[i] = this.storage[i];
   }
   int i = start;
   int j = mid + 1;
   int k = start;
   while( i <= mid && j <= end){
     if(tempArray[i].height <= tempArray[j].height){
       this.storage[k] = tempArray[i];
       i++;
     } else{
       this.storage[k] = tempArray[j];
       j++;
     }
     k++;
   }
   while( i <= mid){
     this.storage[k] = tempArray[i];
     k++;
     i++;
   } 
 }
 
 
 /**
  * Adds a box is the smallest possible shelf where there is room available.
  * Here we assume that there is at least one shelf (i.e. nbShelves >0)
  * @param b
  * @return problem or noProblem
  */
public String addBox (Box b){
  String y = " ";
  //check the amount of free space before adding a box
  int length1 = totalAvailableLength(); 
  //if there is enough height and length on the shortest available shelf, add the box. When box is added, break
  for(int i = 0; i < nbShelves; i++){
    if(storage[i].height >= b.height && storage[i].availableLength >= b.length){
      storage[i].addBox(b);
      y = this.noProblem;
      break;
    }
 }
  //check to see if the total available length has changed because if a box was added, the length will be different. 
  int length2 = totalAvailableLength();
  if(length2 == length1){
    y = this.problem;
  }
  return y;
}

 public int totalAvailableLength(){
  int totalAvailableLength = 0;
  for(int i = 0; i < nbShelves; i++){
    totalAvailableLength = totalAvailableLength + this.storage[i].availableLength;
  }
  return totalAvailableLength;
 }
 /**
  * Adds a box to its corresponding shipping list and updates all the fields
  * @param b
  * @return problem or noProblem 
  */

 public String addToShip (Box b){
   String y = this.problem;
   //run this if block to see if the input is an UrgentBox, if it is add it to the beginning of toShipUrgently
   if(b instanceof UrgentBox){ 
     UrgentBox box = new UrgentBox(b.height, b.length, b.id);
     if(this.toShipUrgently == null){
       this.toShipUrgently = box;
       y = this.noProblem;
     } else{
       box.next = this.toShipUrgently;
       this.toShipUrgently = box;
       y = this.noProblem;
     }
   }
   //This block only runs if b is not an UrgentBox and then adds b to the beginning of toShip. 
   else if(this.toShip == null){
     this.toShip = b;
     y = this.noProblem; 
   } else {
     b.next = this.toShip;
     this.toShip = b;
     y = this.noProblem;
   }
   return y;
 }
// /*
// /**
//  * Find a box with the identifier (if it exists)
//  * Remove the box from its corresponding shelf
//  * Add it to its corresponding shipping list
//  * @param identifier
//  * @return problem or noProblem
//  */
 public String shipBox (String identifier){
   String y = this.problem;
   Box needGo = this.toShip;
   
  //iterates through the shelves to find the box we want  
  for(int i = 0; i<nbShelves; i++){
     /* if the box ID is not found at this iteration, needGo will get a null value and the loop will go on to the next iteration.
      * If it is found, this box will be added to its shipping list*/
     needGo = storage[i].removeBox(identifier);
     if(needGo != null){
       y = addToShip(needGo); 
       return y;
     } else {
       continue;
     }
   }
  return y;  
 }
 
 /**
  * if there is a better shelf for the box, moves the box to the optimal shelf.
  * If there are none, do not do anything
  * @param b
  * @param position
  */
 public void moveOneBox (Box b, int position){
   Box temp = b;
   int boxHeight = b.height;
   int boxLength = b.length;
   int shelfHeight = storage[position].height;
   for(int i = 0; i<nbShelves; i++){
     if(storage[i].height < shelfHeight && storage[i].height >= boxHeight && storage[i].availableLength >= boxLength){
      storage[position].removeBox(b.id);
      storage[i].addBox(b);
      break;
     }
   }
 }
 
 /**
  * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
  */
 public void reorganize (){
   for(int j = 0; j<nbShelves; j++){
   for(int i = 0; i<nbShelves; i++){
     Box temp = storage[i].firstBox;
     if(temp != null){
     temp.next = storage[i].firstBox.next;
       while(temp!=null){
         moveOneBox(temp, i);
         temp = temp.next;
       }
     }
   }
   }
 }
}