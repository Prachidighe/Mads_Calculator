# Mads_Calculator

App contains
1. Login View
2. Home View

# Login View
This user has been added on firebase using firebase console.

# Home View
It has two parts - 1. Displays calculator and 
			      2. Displays history of your calculations.


# 1. Calculator

It follows the MADS Rule.
The display box displays the text entered by the user.
The steps to calculate are:
 
1. When the user clicks “Answer Button” the complete expression will be passed to 
“evaluate” method.
2. The complete string will split into a char array where each element will be given index value.
3. The array object which contains all the values will undergo two checks 
	1. Check if the particular item is operator or number.
	2. If it is an operator is will be pushed to operationValues stack else it will be pushed to numberValues stack.
	3. If we detect two operators in the stack, the precedence will be checked by “hasPrecedence”
Method. If the operator has lesser precedence value then the previous one the calculation will be performed and will be pushed into numberValue stack and the operator will be pushed into operationValue stack and this will continue until both the stack is empty.
	4. Each operator has been set a rank where 1 is the highest rank and 4 is the lowest. So precedence will be checked accordingly.
4. Also, when we get the answer the value will be stored in Firebase database.


# 2. History
1. User can view his/her previous calculations which gets stored in firebase.
