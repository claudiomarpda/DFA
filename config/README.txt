----------------------=====READ-ME=====----------------------
Every file is a full automaton description. 
Each state and its transition are specified as the input pattern.
See the INPUT_TEMPLATE.txt for exactly input file example.
-------------------------------------------------------------

Input pattern:
-------------------------------------------------------------
	Alphabet
	Number of states
	q0, isFinal, c0, f(c0), c1, f(c1)... ci, f(ci)
	q1, isFinal, c0, f(c0), c1, f(c1)... ci, f(ci)
	.
	.
	.
	qi, isFinal, c0, f(c0), c1, f(c1)... ci, f(ci)

	where c is a character of the input string
	f(c) are integer index values
-------------------------------------------------------------


Example 01:
-------------------------------------------------------------
Automata description: 
	{w| w begins with b and ends with a}


ab 				// is the alphabet
4				// is the number of states
0,0,a,1,b,2		// q0, false, f(a)->q1, f(b)->q2
1,0,a,1,b,1		// q1, false, f(a)->q1, f(b)->q1
2,0,a,3,b,2		// q2, false, f(a)->q3, f(b)->q2
3,1,a,3,b,2		// q3, true, f(a)->q3, f(b)->q2

-------------------------------------------------------------

Example 02:
-------------------------------------------------------------
Automata description: 
	{w| w has an even length}


abcdefghijklmnopqrstuvwxyz
2
0,1,abcdefghijklmnopqrstuvwxyz,1
1,0,abcdefghijklmnopqrstuvwxyz,0

-------------------------------------------------------------