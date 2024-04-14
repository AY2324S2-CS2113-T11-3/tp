# Mohamed Ijaaz - Project Portfolio Page

## Overview
Florizz is a digital florist replacement that helps users choose appropriate
flowers to put into a bouquet for various occasions.

### Summary of Contributions
* Click [here](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=ijaaz&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=Ijaaz01&tabRepo=AY2324S2-CS2113-T11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false) to view my contributions.

* **New Feature**: Flower and FlowerDictionary class
  * Added the initial version of both the Flower and FlowerDictionary class to Florizz, along with the startup() method
  that has been used to add flowers into the FlowerDictionary till now. [proof](https://github.com/AY2324S2-CS2113-T11-3/tp/pull/19/files)
  * These two classes form the backbone of our programme and as such it was important that they were implemented properly,
  so that the project could carry on in the future smoothly

* **New Feature**: Storage
  * Implemented the storage functionality for Florizz. This included checking and creating the relevant output directories,
  Creating the necessary output files for logging, automatically saving of all bouquets during the run time as well as recovering 
  said bouquets when the programme is launched again. 
  * This feature was necessary to ensure that using Florizz would be much more practical for both casual users and developers,
  as all the necessary data is stored and organised properly.

* **New Feature**: Save bouquet
  * Implemented the `save` command which would save a bouquet of the users choosing locally, allowing them to refer to the 
  bouquet outside of Florizz
  * The highlight of this function comes from the customized save files for every bouquet created meaning the bouquet 'mum'
  will be saved to 'mum.txt', while 'dad' will be saved to 'dad.txt'. This is a quality of life feature for users as they
  do not have to look through a long text file to find the specific bouquet they want
  
* **New Feature**: Flowers and info command
  * Implemented the initial version of both the `flowers` and `info` command for Florizz. 
  * Highlight of  `flowers` is the option to display all flowers if an occasion was not specified by the user, or just 
  flowers related to a specific occasion if one was specified.

* **Enhancements to Existing Features**
  * Helped to change the logic of the `recommend command` mostly implemented by my teammate Ian, so that it did not kick 
  users out of 'recommend mode' when given a bad input. [proof](https://github.com/AY2324S2-CS2113-T11-3/tp/pull/155/files)
  * Updated the `info` command to allow it to display the information for a flower even if its meaning and ocassions had 
  no entries. This required updating the item key for the fuzzy logic function implemented by my teammate Jeff. [proof](https://github.com/AY2324S2-CS2113-T11-3/tp/pull/157/files)

* **Documentation**
  * **UG**:
  ### List all available flowers: `flowers`
  List all available flowers in the database currently, also able to filter presented flowers according to occasion
  ### Save a bouquet to device: `save`
  Saves chosen bouquet, if it exists, locally to the users device
  
  Format: `save <bouquetName>`
  
  - Bouquet must exist before it can be saved
  
  Examples:
  - `save moms bouquet`
  
  Expected Output:
  ```
  Successfully saved moms bouquet. You can find it at 'florizz-out/saved/moms bouquet.txt
  ```
  ### Autosave
  Florizz automatically backs up all your bouquet data onto your device in a `FlorizzBouquets.txt` file.
  As such the user can transfer their bouquet data between devices by simply moving the text file to the `florizz-out/data/`
  folder. However, editing this text file is not recommended as the format is very specific, so users
  should do so at their own risk

  * **DG**:
  ![Add Flower Command Diagram](/docs/UML-diagrams/Ijaaz/(old)Ijaaz-UML.png)
  
