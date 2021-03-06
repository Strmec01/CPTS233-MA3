package test1;

import java.util.Vector;

/*
 *  Microassignment: Probing Hash Table addElement and removeElement
*
*  LinearHashTable: Yet another Hash Table Implementation
* 
*  Contributors:
*    Bolong Zeng <bzeng@wsu.edu>, 2018
*    Aaron S. Crandall <acrandal@wsu.edu>, 2019
* 
*  Copyright:
*   For academic use only under the Creative Commons
*   Attribution-NonCommercial-NoDerivatives 4.0 International License
*   http://creativecommons.org/licenses/by-nc-nd/4.0
*/


class LinearHashTable<K, V> extends HashTableBase<K, V>
{

	// Linear and Quadratic probing should rehash at a load factor of 0.5 or higher
   private static final double REHASH_LOAD_FACTOR = 0.5;
   
 
   // Constructors
   public LinearHashTable()
   {
       super();
   }

   public LinearHashTable(HasherBase<K> hasher)
   {
       super(hasher);
   }

   public LinearHashTable(HasherBase<K> hasher, int number_of_elements)
   {
       super(hasher, number_of_elements);
   }

   // Copy constructor
   public LinearHashTable(LinearHashTable<K, V> other)
   {
       super(other);
	}
   
  
   // ***** MA Section Start ************************************************ //

   //Jocelyn Strmec GIT HUB:https://github.com/Strmec01/CPTS233-MA3.git
   
   // Concrete implementation for parent's addElement method
   public void addElement(K key, V value)
   {	
       // Check for size restrictions
       resizeCheck();

       // Calculate hash based on key
       int hash = super.getHash(key);
      
       //if slot.isEmpty == False AND slot.getValue != value then 
       //while (_items.elementAt(hash).isEmpty == false) hash += 1
       //else slot.isEmppty == False and slot.getValue == value then return
      while(_items.elementAt(hash).isEmpty() == false) {//In case of Collision increase hash
   	       hash ++;
       }
       if(_items.elementAt(hash).isEmpty() == true ) {
    	   //adds appropriate values to hashtable
    	   _items.elementAt(hash).setKey(key);
     	   _items.elementAt(hash).setValue(value);
    	   _items.elementAt(hash).setIsEmpty(false);
    	   _number_of_elements++;
       }
       
       // Remember how many things we are presently storing (size N)
   	//  Hint: do we always increase the size whenever this function is called?
       return;

   }

   // Removes supplied key from hash table
   public void removeElement(K key)
   {  
	   // Calculate hash from key
		// int hash = super.getHash(key);
		 
		 //HashItem<K, V> slot = _items.elementAt(hash);
		 
		if(super.hasKey(key)) {//checks if hashtable key exists
			int index = _items.size()-1;//sets count to number of length of hashtable
			boolean done = false;//base check for while statement
			while(!done) {
				//only enters removal is key is key to be removed
				if(_items.elementAt(index).getKey()== key ) {
			//lazy deletion with setIsEmpty, if it wasn't lazy for test cases, it'd be setKey and setValue to null
					 _items.elementAt(index).setIsEmpty(true);
					 _number_of_elements--;
					 done = true;
				}else {
					if(index == 0)//prevents looping to -1 if error was made
						break;
					index--;//reduces index
					
				}
			}
	          
		}
	       // MA TODO: find slot to remove. Remember to check for infinite loop!
	      
	       // Make sure decrease hashtable size
	   	//  Hint: do we always reduce the size whenever this function is called?
       return;
       
   }
   
   // ***** MA Section End ************************************************ //
   

   // Public API to get current number of elements in Hash Table
	public int size() {
		return this._number_of_elements;
	}

   // Public API to test whether the Hash Table is empty (N == 0)
	public boolean isEmpty() {
		return this._number_of_elements == 0;
	}
   
   // Returns true if the key is contained in the hash table
   public boolean containsElement(K key)
   {
      // int hash = super.getHash(key);
       /*HashItem<K, V> slot = _items.elementAt(hash);
       if(slot.getValue() != null) {
    	   return true;*/
       //}
      if(_items.elementAt(super.getHash(key))== key) {
    	   return true;
       }else {
       return false;
       }
   }
   
   // Returns the item pointed to by key
   public V getElement(K key)
   {
       int hash = super.getHash(key);
		/*
		 * HashItem<K, V> slot = _items.elementAt(hash); 
		 * 
		 * V value = slot.getValue()
		 */;
       V value = _items.elementAt(hash).getValue();
       return value;
   }

   // Determines whether or not we need to resize
   //  to turn off resize, just always return false
   protected boolean needsResize()
   {
       // Linear probing seems to get worse after a load factor of about 50%
       if (_number_of_elements > (REHASH_LOAD_FACTOR * _primes[_local_prime_index]))
       {
           return true;
       }
       return false;
   }
   
   // Called to do a resize as needed
   protected void resizeCheck()
   {
       // Right now, resize when load factor > 0.5; it might be worth it to experiment with 
       //  this value for different kinds of hashtables
       if (needsResize()){
           _local_prime_index++;

           HasherBase<K> hasher = _hasher;
           LinearHashTable<K, V> new_hash = new LinearHashTable<K, V>(hasher, _primes[_local_prime_index]);

           for (HashItem<K, V> item: _items)
           {
               if (item.isEmpty() == false)
               {
                   // Add element to new hash table
                   new_hash.addElement(item.getKey(), item.getValue());
               }
           }

           // Steal temp hash object's internal vector for ourselves
           _items = new_hash._items;
       }
   }

   // Debugging tool to print out the entire contents of the hash table
	public void printOut() {
		System.out.println(" Dumping hash with " + _number_of_elements + " items in " + _items.size() + " buckets");
		System.out.println("[X] Key	| Value	| Deleted");
		for( int i = 0; i < _items.size(); i++ ) {
			HashItem<K, V> curr_slot = _items.get(i);
			System.out.print("[" + i + "] ");
			System.out.println(curr_slot.getKey() + " | " + curr_slot.getValue() + " | " + curr_slot.isEmpty());
		}
	}

	public Vector<HashItem<K, V>> getItems() {
		return super.getItems();
	}
}
