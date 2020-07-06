package assignment3;

public class Building {

 OneBuilding data;
 Building older;
 Building same;
 Building younger;
 
 public Building(OneBuilding data){
  this.data = data;
  this.older = null;
  this.same = null;
  this.younger = null;
 }
 
 public String toString(){
  String result = this.data.toString() + "\n";
  if (this.older != null){
   result += "older than " + this.data.toString() + " :\n";
   result += this.older.toString();
  }
  if (this.same != null){
   result += "same age as " + this.data.toString() + " :\n";
   result += this.same.toString();
  }
  if (this.younger != null){
   result += "younger than " + this.data.toString() + " :\n";
   result += this.younger.toString();
  }
  return result;
 }
 
 public Building addBuilding (OneBuilding b){
   if(this.data == null){
     this.data = b;
   } 
   else if(b.yearOfConstruction < this.data.yearOfConstruction){
     if(this.older != null){
       if(b.yearOfConstruction == this.older.data.yearOfConstruction && b.height > this.older.data.height){
         OneBuilding temp = this.older.data;
         this.older.data = b;
         addBuilding(temp);
       } else {
       this.older.addBuilding(b);
       }
     }else{
       this.older = new Building(b);
     }
   } 
   else if (b.yearOfConstruction > this.data.yearOfConstruction){
     if(this.younger != null){
       if(b.yearOfConstruction == this.younger.data.yearOfConstruction && b.height > this.younger.data.height){
         OneBuilding temp = this.younger.data;
         this.younger.data = b;
         addBuilding(temp);
       } else {
       this.younger.addBuilding(b);
       }
     } else {
       this.younger = new Building(b);
     }
   }
   else if (b.yearOfConstruction == this.data.yearOfConstruction){
     if(this.data.equals(b)){
       return this;
     }
     else if(b.height > this.data.height){
       OneBuilding temp = this.data;
       this.data = b;
       addBuilding(temp);
     }
     else if(this.same != null && this.same.data.height > b.height){
       this.same.addBuilding(b);
     } 
     else if(this.same != null && this.same.data.height <= b.height){
       Building temp = new Building(b);
       temp.same = this.same;
       this.same = temp;
     }else{
       this.same = new Building(b);
     }
   }
   return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 
 public Building addBuildings (Building b){
   addBuilding(b.data);
   if(b.older != null){
     addBuildings(b.older);
   }
   if(b.same != null){
     addBuildings(b.same);
   }
   if(b.younger != null){
     addBuildings(b.younger);
   } 
  return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public Building removeBuilding (OneBuilding b){
   
   //the root of the tree is the node to remove
   if(this.data.equals(b)){
     if(this.same != null){
       this.data = this.same.data;
       this.same = this.same.same;
       return this;
     }
     //If there we have an older child
     else if(this.older != null){
       Building temp = this.younger;
       if(temp != null){
         this.older.addBuildings(temp);
       }
       return this.older;
     }
     //If we have a younger child
     else if(this.younger != null ){
       this.data = this.younger.data;
       Building temp = this.younger;
       this.younger = null;
       this.addBuildings(temp);
       return this;
     }
   }
   
   //We want to remove is the child that has the same year of construction.
   else if(this.same != null && this.same.data.equals(b)){
     if(this.same.same != null){
       this.same = this.same.same;
     } else{
       this.same = null;
     }
   }
   //We want to remove the child with an older year of construction.
   else if(this.older != null && this.older.data.equals(b)){
     //RUN this code block if the node we want to delete is a leaf
     if(this.older.older == null && this.older.same == null && this.older.younger == null){
       this.older = null;
     }
     //This block only runs when the node has a "same" child
     else if(this.older.same != null){
       this.older.data = this.older.same.data;
       this.older.same = this.older.same.same;
     }
     //This block only runs if the node has an "older" child
     else if(this.older.same == null && this.older.older != null){
       Building temp = this.older.younger;
       this.older = this.older.older;
       this.older.younger = null;
       if(temp != null){
         this.older.addBuildings(temp);
       }
     }
     //This block only runs if the node has a left child
     else{
       this.older = this.older.younger;
     }
   }
   //We want to remove the child with the younger year of construction
   else if(this.younger != null && this.younger.data.equals(b)){
     //RUN this code block if the node we want to delete is a leaf
     if(this.younger.older == null && this.younger.same == null && this.younger.younger == null){
       this.younger = null;
     }
     //This block only runs if the node has a "same" child
     else if(this.younger.same != null){
       this.younger.data = this.younger.same.data;
       this.younger.same = this.younger.same.same;
     }
     //This block only runs if the node has an "older" child
     else if(this.younger.same == null && this.younger.older != null){
       Building temp = this.younger.younger;
       this.younger = this.younger.older;
       if(temp != null){
         this.younger.addBuildings(temp);
       }
     }
     //This block only runs if the node has a left child
     else{ 
       this.younger = this.younger.younger;
     }
   }
  
   else if(b.yearOfConstruction < this.data.yearOfConstruction && this.older != null){
     this.older.removeBuilding(b);
   }
   else if(b.yearOfConstruction > this.data.yearOfConstruction && this.younger != null){
     this.younger.removeBuilding(b);
   }
   else if(b.yearOfConstruction == this.data.yearOfConstruction && this.same != null){
     this.same.removeBuilding(b);
   } else{
     return this;
   }
   
   
   return this; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }

 
 public int oldest(){
  int age = 0;
  age = this.data.yearOfConstruction;
  if(this.older == null){
    return age;
  }
  return this.older.oldest(); // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int highest(){
   int height = this.data.height;
     if(this.younger != null && this.older != null && this.same != null){
       int temp1 = this.younger.highest();
       int temp2 = this.older.highest(); 
       int temp3 = this.same.highest();
       if(temp1 > height){
         height = temp1;
       }
       if(temp2 > height){
         height = temp2;
       }
       if(temp3 > height){
         height = temp3;
       }
       return height;
     } 
     else if(this.younger != null && this.older != null){
       int temp1 = this.younger.highest();
       int temp2 = this.older.highest(); 
       if(temp1 > height){
         height = temp1;
       }
       if(temp2 > height){
         height = temp2;
       }
     }
     else if(this.younger != null && this.same != null){
       int temp1 = this.younger.highest();
       int temp3 = this.same.highest();
       if(temp1 > height){
         height = temp1;
       }
       if(temp3 > height){
         height = temp3;
       }
       return height;
     }
     else if(this.same != null && this.older != null){
       int temp2 = this.older.highest();
       int temp3 = this.same.highest();
       if(temp2 > height){
         height = temp2;
       }
       if(temp3 > height){
         height = temp3;
       }
       return height;
     }
     else if(this.younger != null){
       int temp1 = this.younger.highest();
       if(temp1 > height){
         height = temp1;
       }
     }
     else if(this.same != null){
       int temp3 = this.same.highest();
       if(temp3 > height){
         height = temp3;
       }
     }
     else if(this.older != null){
       int temp2 = this.older.highest();
       if(temp2 > height){
         height = temp2;
       }
     }
     else{
       return height;
     }
     return height;
   }
 
 public OneBuilding highestFromYear (int year){
   if(this.data.yearOfConstruction == year){
     return this.data;
   }
   else if(year > this.data.yearOfConstruction){
     if(this.younger != null){
       return this.younger.highestFromYear(year);
     }else{
       return null;
     }
   }
   else if(year < this.data.yearOfConstruction){
     if(this.older != null){
       return this.older.highestFromYear(year);
     }else{
       return null;
     }
   } 
   return null; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int numberFromYears (int yearMin, int yearMax){
   if(yearMin > yearMax){
     return 0;
   }
   
   if(this.data.yearOfConstruction >= yearMin && this.data.yearOfConstruction <= yearMax){
     if(this.younger != null && this.older != null && this.same != null){
       return 1 + this.younger.numberFromYears(yearMin, yearMax) + this.older.numberFromYears(yearMin, yearMax) + 
         this.same.numberFromYears(yearMin, yearMax);
     } 
     else if(this.younger != null && this.older != null){
       return 1 + this.younger.numberFromYears(yearMin, yearMax) + this.older.numberFromYears(yearMin, yearMax);
     }
     else if(this.younger != null && this.same != null){
       return 1 + this.younger.numberFromYears(yearMin, yearMax) + this.same.numberFromYears(yearMin, yearMax);
     }
     else if(this.same != null && this.older != null){
       return 1 + this.same.numberFromYears(yearMin, yearMax) + this.older.numberFromYears(yearMin, yearMax);
     }
     else if(this.younger != null){
       return 1 + this.younger.numberFromYears(yearMin, yearMax);
     }
     else if(this.same != null){
       return 1 + this.same.numberFromYears(yearMin, yearMax);
     }
     else if(this.older != null){
       return 1 + this.older.numberFromYears(yearMin, yearMax);
     }
     else{
       return 1;
     }
   }
   //The root is older than the Min.
   else if(this.data.yearOfConstruction < yearMin ){
     if(this.younger != null){
       return this.younger.numberFromYears(yearMin, yearMax);
     } else{
       return 0;
     }
   } else{
     //The root is younger than the Max
     if(this.older != null){
       return this.older.numberFromYears(yearMin, yearMax);
     } else{
       return 0;
     }
   }
   // return 0; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
 public int[] costPlanning (int nbYears){
   int[] cost = new int[nbYears];
   for(int i = 0; i < nbYears; i++){
     if(this.data.yearForRepair == 2018 + i){
       cost[i] = this.data.costForRepair;
     
     if(this.younger != null && this.older != null && this.same != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.younger.costPlanning(nbYears)[j] + this.older.costPlanning(nbYears)[j] + 
           this.same.costPlanning(nbYears)[j];
       }
       return cost;
     } 
     else if(this.younger != null && this.older != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.younger.costPlanning(nbYears)[j] + this.older.costPlanning(nbYears)[j];
       }
       return cost;
     }
     else if(this.younger != null && this.same != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.younger.costPlanning(nbYears)[j] + this.same.costPlanning(nbYears)[j];
       }
       return cost;
     }
     else if(this.same != null && this.older != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.same.costPlanning(nbYears)[j] + this.older.costPlanning(nbYears)[j];
       }
       return cost;
     }
     else if(this.younger != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.younger.costPlanning(nbYears)[j];
       }
       return cost;
     }
     else if(this.same != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.same.costPlanning(nbYears)[j];
       }
       return cost;
     }
     
     else if(this.older != null){
       for(int j = 0; j < nbYears; j++){
         cost[j] += this.older.costPlanning(nbYears)[j];
       }
       return cost;
     }
     else{
       return cost;
     }
   }
   }
   if(this.same != null){
     return this.same.costPlanning(nbYears);
   }
   if(this.older != null){
     return this.older.costPlanning(nbYears);
   }
   if(this.younger!= null){
     return this.younger.costPlanning(nbYears);
   }
   return cost;
   // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
 }
 
}