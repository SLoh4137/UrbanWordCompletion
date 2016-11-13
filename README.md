# UrbanWordCompletion
Project created by Stephan Loh, Timothy Zhou, David Hsu, and Albert Wang

UrbanWordCompletion looks up the word given by the user and tries to find words similar to it on Urban Dictionary's popular page.
The program creates several threads to parse the html page and calculate the similarity between parsed words.
This program uses the Levenshtein algorithm provided by RosettaCode at http://rosettacode.org/wiki/Levenshtein_distance#Java 

UrbanWordCompletion allows you to specify the number of words you want to retrieve and displays them in a text box.

Note: Since our program uses Urban Dictionary, expect vulgar language.
