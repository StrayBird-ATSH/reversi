import time
direction = ((-1, -1), (-1, 0), (-1, 1),\
                 (0, -1), (0, 1),\
                 (1, -1), (1, 0), (1, 1))

dimension = 0
mark = ['.', 'X', 'O']
board = []
humanMoveIllegal = 0                                             #to mark if human has made an illegal move
computerValidMove = 1
humanValidMove = 1

def Init_board():                                                #Initiate the board
    global board
    board = [[0 for i in range(dimension)] for i in range(dimension)]
    dmsMid = dimension // 2
    board[dmsMid - 1][dmsMid-1] = -1
    board[dmsMid - 1][dmsMid] = 1
    board[dmsMid][dmsMid - 1] = 1
    board[dmsMid][dmsMid] = -1
    return board

def printBoard():
    rowTop = [chr(ord('a') + i) for i in range(dimension)]
    print('  %s' % (' '.join(rowTop)))
    for index, row in enumerate(board):
        rowPrint = [mark[check] for check in row]
        print('{0} {1}'.format(chr(ord('a') + index), ' '.join(rowPrint)))

def computer_move(colour):                                       #excute computer move
    global board
    global computerValidMove
    computerValidMove = 1                                        #to mark if computer has valid moves
    scoreDict = {}
    for rowNum in range(dimension):
        for colNum in range(dimension):
            scoreDict[(rowNum, colNum)] = position_score(rowNum, colNum, colour)
    scoreSorted = sorted(scoreDict.items(), key = lambda x: (-x[1], x[0][0], x[0][1]))
    scoreMaxPoint = scoreSorted[0]
    if not scoreMaxPoint[1]:                                     #Max score == 0, computer has no valid move now.
        computerValidMove = 0
        print('%s player has no valid move.' % (mark[colour]))
        return board
    
    rowComputer = scoreMaxPoint[0][0]
    colComputer = scoreMaxPoint[0][1]
    flip(rowComputer, colComputer, colour)
    rowLetter = chr(rowComputer + ord('a'))
    colLetter = chr(colComputer + ord('a'))
    print('Computer places %s at %s%s.' % (mark[colour], rowLetter, colLetter))
    return board

def human_move(colour):                                          #execute human move
    global board
    global humanMoveIllegal
    global humanValidMove
    humanValidMove = 1                                           #to mark if human has valid moves
    for rowNum in range(dimension):
        for colNum in range(dimension):
            found = check_legal_move(rowNum, colNum, colour)
            if found:
                while True:
                    try:
                        positionHm = input('Enter move for %s (RowCol): ' % (mark[colour]))
                        rowHuman = ord(positionHm[0]) - ord('a')
                        colHuman = ord(positionHm[1]) - ord('a')
                        if (0 <= rowHuman <= (dimension - 1)) and (0 <= colHuman <= (dimension - 1)):
                            break
                    except:
                        print('Invalid input. Please try again...')
                    else:
                        checkIfContinue = input('Are you sure to quit? Please try again...\nEnter move for %s (RowCol): ' % (mark[colour]))
                        rowHuman = ord(checkIfContinue[0]) - ord('a')
                        colHuman = ord(checkIfContinue[1]) - ord('a')
                        break

                if check_legal_move(rowHuman, colHuman, colour):
                    flip(rowHuman, colHuman, colour)
                    return board
                else:
                    humanMoveIllegal = 1                         #to mark that human has made an illegal move
                    return board
    humanValidMove = 0
    print('%s player has no valid move.' % (mark[colour]))
    return board
                    
                    

def check_board():                                               #to check if there is any more valid move in the board
    if humanMoveIllegal:
        return False
    for row in range(dimension):
        for col in range(dimension):
            blackMove = check_legal_move(row, col, 1)
            whiteMove = check_legal_move(row, col, -1)
            if blackMove or whiteMove:
                return True
    print('Both players have no valid move.')
    return False      
    
    
def check_legal_move(row, col, colour):                          #to check if player of colour have any legal move
    found = False
    if (0 <= row <= (dimension - 1)) and (0 <= col <= (dimension - 1)) and (not board[row][col]):
        for i in direction:
            pstRow = row + i[0]
            pstCol = col + i[1]
            if (0 <= pstRow <= (dimension - 1)) and (0 <= pstCol <= (dimension - 1)) and (colour == -board[pstRow][pstCol]):
                colourOppo = -colour
                while (not found) and (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)):
                    pstRow += i[0]
                    pstCol += i[1]
                    if (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)) and (board[pstRow][pstCol] != colourOppo):
                        if board[pstRow][pstCol]:
                            found = True
                            return found
                        else:
                            break
    return found

def flip(row, col, colour):                                      #to flip the pieces in all directions
    global board
    for i in direction:
        found = False
        pstRow = row + i[0]
        pstCol = col + i[1]
        if (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)) and (colour == -board[pstRow][pstCol]):
                colourOppo = -colour
                count = 1
                while (not found) and (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)):
                    pstRow += i[0]
                    pstCol += i[1]
                    count += 1
                    if (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)) and (board[pstRow][pstCol] != colourOppo):
                        if board[pstRow][pstCol]:
                            found = True
                            for num in range(count):
                                pstRow -= i[0]
                                pstCol -= i[1]
                                board[pstRow][pstCol] = colour
                        else:
                            break
    return board

def position_score(row, col, colour):                            #to calculate the score of each position
    scoreList = []
    if not board[row][col]:
        for i in direction:
            found = False
            pstRow = row + i[0]
            pstCol = col + i[1]
            if (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)) and (colour == -board[pstRow][pstCol]):
                colourOppo = -colour
                scoreCount = 0
                while (not found) and (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)):
                    pstRow += i[0]
                    pstCol += i[1]
                    scoreCount += 1
                    if (0 <= pstRow <= (dimension -1)) and (0 <= pstCol <= (dimension -1)) and (board[pstRow][pstCol] != colourOppo):
                        if board[pstRow][pstCol]:
                            found = True
                            score = 0
                            for num in range(scoreCount):
                                score += 1
                            scoreList.append(score)
                        else:
                            break
    if scoreList:
        return(sum(scoreList))
    else:
        return 0 


def gameover(colourComputer):                                    #when game over, output and count the score of both sides
    if humanMoveIllegal:
        print('Invalid move.')
        print('Game over.')
        print('%s player wins.' % (mark[colourComputer]))
        return
    
    print('Game over.')
    scoreList = [0 for i in range(3)]
    for row in board:
        for check in row:
            scoreList[check] += 1
            
    print('X : O = %d : %d' % (scoreList[1], scoreList[-1]))
    
    if scoreList[1] == scoreList[-1]:
        print('Draw!')
    if scoreList[1] > scoreList[-1]:
        print('%s player wins.' % (mark[1]))
    if scoreList[1] < scoreList[-1]:
        print('%s player wins.' % (mark[-1]))

    return (scoreList[1], scoreList[-1])

def saveInfo(scoreRecord, colourComputer, start, end):           #to save the information of the game into 'reversi.csv'
    player = (0, 'computer', 'human')
    blackPlayer = player[colourComputer]                         #to figure out the blackPlayer
    whitePlayer = player[-colourComputer]                        #to figure out the whitePlayer
    
    with open('reversi.csv', 'a+') as handler:
        handler.write("{},{},{}*{},{},{},".format(time.strftime('%Y%m%d %H:%M:%S'),
                                                  int(end-start), dimension, dimension,
                                                  blackPlayer, whitePlayer))
        if humanMoveIllegal:
            handler.write('Human gave up.\n')
        else:
            handler.write('%d to %d\n' % (scoreRecord[0], scoreRecord[1])) 
                           
def main():
    global dimension
    global board
    global humanMoveIllegal
    while True:
        humanMoveIllegal = 0
        start = time.time()
        while True:
            try:
                dimension = int(input('Enter the board dimension: '))
                if (dimension % 2 == 0) and (4 <= dimension <= 26):
                    break      
            except:
                print('Invalid input. Please try again...')
            else:
                print('The board dimension should be an even number between 4 and 26. Please try again...')
                
        board = Init_board()
        while True:
            try:
                colourCmptStr = (input('Computer plays (X/O) : ')).upper()
                colourComputer = mark.index(colourCmptStr)
                break
            except:
                print('Invalid input. Please try again...')
                
        if colourComputer != 1:
            colourComputer = -1
        colourHuman = -colourComputer
        printBoard()
        if colourComputer == 1:
            while check_board():
                board = computer_move(colourComputer)
                if computerValidMove:
                    printBoard()
                if check_board():
                    board = human_move(colourHuman)
                    if humanMoveIllegal:
                        break
                    if humanValidMove:
                        printBoard()
                        
            end = time.time()           
            scoreRecord = gameover(colourComputer)
            saveInfo(scoreRecord, colourComputer, start, end)

        if colourComputer == -1:
            while check_board():
                board = human_move(colourHuman)
                if humanMoveIllegal:
                    break
                if humanValidMove:
                    printBoard()   
                if check_board():
                    board = computer_move(colourComputer)
                    if computerValidMove:
                        printBoard()

            end = time.time()     
            scoreRecord = gameover(colourComputer)
            saveInfo(scoreRecord, colourComputer, start, end)

        ifContinue = (input('Would you like to Continue? (Y/N) ')).lower()
        if ifContinue != 'y':
            print('Goodbye!')
            break

if __name__ == '__main__':
    main()
