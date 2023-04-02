import os
import json

# Create a function named countWords_1() that takes two file names as the inputs. 
# The first one is the input file’s name (a text file) and 
# the second one is the output file’s name (a JSON file). 
# The function will count the number of times the words in the input text
# file occur, Calculate the words’ frequency in percentage
# then write the result in percentage descending order
# (in JSON format) into the output file.
def countWords_2(file1, file2):
    # open and read file
    with open(file1, 'r') as f:
        data = f.read().lower() # convert all words into lower case

    # replace line break with space
    wordListRaw = data.replace('\n', ' ')
    # split all words into seperate item in list
    wordList = wordListRaw.split()

    # add a new non-existed word into dictionary 
    # and change frequency each time the word repeat again
    # as key and value pairs
    frequency = {}
    for word in wordList:
        if word not in frequency:
            frequency[word] = 0
        frequency[word] += 1
    
    # calculate the frequency in percentage and round in 2 digit in decimals
    # change keys' value into percentage frequency
    for key, value in frequency.items():
        frequency[key] = round((value/len(wordList))*100, 2)
    
    # add a new dictionary
    sortFrequency = {}
    # sort value of frequency in descending order
    sortValues = sorted(frequency.values(), reverse=True)
    # add frequency item into sort frequency dictionary
    # follow the order of sorted values
    for sortValue in sortValues:
        for key, value in frequency.items():
            if value == sortValue:
                sortFrequency[key] = value

    # write dictionary result into json file
    with open(file2, 'w') as json_file:
        json.dump(sortFrequency, json_file)

# example
countWords_2('Assignment2_Sample_Inputs/sample.txt', 'sample_out_2.json')