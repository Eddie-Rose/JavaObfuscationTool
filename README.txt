Custom java obfuscation tool which iterates through a projects src directory 
and encrypts and/ all json file using an xor encryption algorithm. The key 
of the xor encryption algorithm is stored in a specific pixel stored in an image.

This Java obfuscation tool is fixed to only work with a project folder named: 
COMPSCI702Project, and the src folders must be within the file directory: COMPSCI702Project/app/src/main.

To obfuscate a project folder the following steps and conditions must be followed:

Conditions:
* The project folder (COMPSCI702Project) should be in the same directory as the JavaObfuscationTool.jar file 

* Project folder name: Name should be specfically -> "COMPSCI702Project"

* src directory: The src folder should be a sub directory of the following heirarchy -> COMPSCI702Project/app/src/main

* Windows environment


Steps:

1. Set the terminal environment to be the same directory as the JavaObfuscationTool.jar file

2. type the following command in the terminal: java -jar JavaObfuscationTool.jar



The terminal should print off all the json files that the program has found and encrypted 
