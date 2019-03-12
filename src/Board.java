import java.util.ArrayList;

public class Board {
    int size;
    private Piece[][] pieces;
    int comCount = 2;
    int userCount = 2;
    char computerPieceName = '0';
    char userPieceName = '0';
    private Color userColor;

    Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }

    Color getUserColor() {
        return userColor;
    }

    void setUserColor(Color userColor) {
        if (userColor == Color.BLACK) {
            computerPieceName = 'O';
            userPieceName = 'X';
        } else {
            computerPieceName = 'X';
            userPieceName = 'O';
        }
        this.userColor = userColor;
        pieces[size / 2 - 1][size / 2 - 1] = new WhitePiece();
        pieces[size / 2][size / 2] = new WhitePiece();
        pieces[size / 2 - 1][size / 2] = new BlackPiece();
        pieces[size / 2][size / 2 - 1] = new BlackPiece();
    }


    boolean checkCanPlace(boolean isUser) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (pieces[i][j] == null && calculateScore(i, j, isUser) > 0) return true;
        return false;
    }




    /*     PLACE-ABLE CHECKER ----START-----     */

    boolean placeable(Color color) {
        ArrayList<Color> colorSequence;
//        In row direction
        if (checkIterator(color, true)) return true;
//        In column direction
        if (checkIterator(color, false)) return true;
//        In diagonal direction
        if (checkDiagonalIterator(color, false)) return true;
        if (checkDiagonalIterator(color, true)) return true;
//        In counter-diagonal direction
        for (int i = 0; i < size; i++) {
            int row = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && row >= 0; j++, row--)
                if (checkCanPlace(color, row, j, colorSequence)) return true;
        }
        for (int j = 1; j < size; j++) {
            int column = j;
            colorSequence = new ArrayList<>();
            for (int i = size - 1; i >= 0 && column < size; i--, column++)
                if (checkCanPlace(color, i, column, colorSequence)) return true;
        }
        return false;
    }

    private boolean checkIterator(Color color, boolean isRow) {
        ArrayList<Color> colorSequence;
        for (int i = 0; i < size; i++) {
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size; j++)
                if (isRow ? checkCanPlace(color, i, j, colorSequence) :
                        checkCanPlace(color, j, i, colorSequence))
                    return true;
        }
        return false;
    }


    private boolean checkDiagonalIterator(Color color, boolean isLower) {
        ArrayList<Color> colorSequence;
        for (int i = 0; i < size; i++) {
            int k = i;
            colorSequence = new ArrayList<>();
            for (int j = 0; j < size && k < size; j++, k++)
                if (!isLower ? checkCanPlace(color, j, k, colorSequence) :
                        checkCanPlace(color, k, j, colorSequence))
                    return true;
        }
        return false;
    }

    private boolean checkCanPlace(Color color, int i, int j, ArrayList<Color> colorSequence) {
        Color inverseColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        Color currentColor;
        if (pieces[i][j] == null) currentColor = Color.NULL;
        else currentColor = pieces[i][j].color;
        if (colorSequence.size() == 0) colorSequence.add(currentColor);
        else if (colorSequence.get(colorSequence.size() - 1) != currentColor) {
            colorSequence.add(currentColor);
            return colorSequence.size() >= 3 &&
                    colorSequence.get(colorSequence.size() - 2) == inverseColor &&
                    !(colorSequence.get(colorSequence.size() - 1) == Color.NULL &&
                            colorSequence.get(colorSequence.size() - 3) == Color.NULL);
        }
        return false;
    }


    /*     PLACE-ABLE CHECKER ----END-----     */


    void placeOpt() {
        int highestScore = 0;
        int currentScore;
        int optimalI = 0, optimalJ = 0;
        ArrayList<Integer> flipRow = new ArrayList<>();
        ArrayList<Integer> flipColumn = new ArrayList<>();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (pieces[i][j] == null) {
                    currentScore = calculateScore(i, j, false);
                    if (currentScore > highestScore) {
                        optimalI = i;
                        optimalJ = j;
                        highestScore = currentScore;
                    }
                }
        System.out.println("Computer places " + computerPieceName + " at " +
                (char) ('a' + optimalI) + (char) ('a' + optimalJ) + ".");
        flip(optimalI, optimalJ, userColor);
        System.out.println(this);
    }

    void flip(int optimalI, int optimalJ, Color userColor) {
        ArrayList<Integer> flipRow = new ArrayList<>();
        ArrayList<Integer> flipColumn = new ArrayList<>();
        boolean isUser = this.userColor != userColor;
        pieces[optimalI][optimalJ] = userColor == Color.BLACK ? new WhitePiece() : new BlackPiece();
        if (isUser) userCount++;
        else comCount++;
        if (optimalI >= 1 && pieces[optimalI - 1][optimalJ] != null && pieces[optimalI - 1][optimalJ].color == userColor) {
            for (int row = optimalI - 1; row >= 0; row--) {
                if (pieces[row][optimalJ] == null) break;
                else if (pieces[row][optimalJ].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(optimalJ);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
//        Left
        if (optimalJ >= 1 && pieces[optimalI][optimalJ - 1] != null && pieces[optimalI][optimalJ - 1].color == userColor) {
            for (int column = optimalJ - 1; column >= 0; column--) {
                if (pieces[optimalI][column] == null) break;
                else if (pieces[optimalI][column].color == userColor) {
                    flipRow.add(optimalI);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());

                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
//        Down
        if (optimalI < size - 1 && pieces[optimalI + 1][optimalJ] != null && pieces[optimalI + 1][optimalJ].color == userColor) {
            for (int row = optimalI + 1; row < size; row++) {
                if (pieces[row][optimalJ] == null) break;
                else if (pieces[row][optimalJ].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(optimalJ);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
//        Right
        if (optimalJ < size - 1 && pieces[optimalI][optimalJ + 1] != null && pieces[optimalI][optimalJ + 1].color == userColor) {
            for (int column = optimalJ + 1; column < size; column++) {
                if (pieces[optimalI][column] == null) break;
                else if (pieces[optimalI][column].color == userColor) {
                    flipRow.add(optimalI);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
        //        Up-left
        if (optimalI >= 1 && optimalJ >= 1 && pieces[optimalI - 1][optimalJ - 1] != null && pieces[optimalI - 1][optimalJ - 1].color == userColor) {
            for (int row = optimalI - 1, column = optimalJ - 1; row >= 0 && column >= 0; row--, column--) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }


        //        Up-right
        if (optimalI >= 1 && optimalJ < size - 1 && pieces[optimalI - 1][optimalJ + 1] != null && pieces[optimalI - 1][optimalJ + 1].color == userColor) {
            for (int row = optimalI - 1, column = optimalJ + 1; row >= 0 && column < size; row--, column++) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
        //        Down-left
        if (optimalI < size - 1 && optimalJ >= 1 && pieces[optimalI + 1][optimalJ - 1] != null && pieces[optimalI + 1][optimalJ - 1].color == userColor) {
            for (int row = optimalI + 1, column = optimalJ - 1; row < size && column >= 0; row++, column--) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }


        //        Down-right
        if (optimalI < size - 1 && optimalJ < size - 1 && pieces[optimalI + 1][optimalJ + 1] != null && pieces[optimalI + 1][optimalJ + 1].color == userColor) {
            for (int row = optimalI + 1, column = optimalJ + 1; row < size && column < size; row++, column++) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) {
                    flipRow.add(row);
                    flipColumn.add(column);
                } else {
                    for (int i = 0; i < flipRow.size(); i++)
                        pieces[flipRow.get(i)][flipColumn.get(i)] = (userColor == Color.BLACK ? new WhitePiece() : new BlackPiece());
                    if (isUser) {
                        userCount += flipRow.size();
                        comCount -= flipRow.size();
                    } else {
                        userCount -= flipRow.size();
                        comCount += flipRow.size();
                    }
                    break;
                }
            }
            flipColumn = new ArrayList<>();
            flipRow = new ArrayList<>();
        }
    }


    int calculateScore(int i, int j, boolean isUser) {
        Color userColor = this.userColor;
        if (isUser) userColor = userColor == Color.BLACK ? Color.WHITE : Color.BLACK;
        int score = 0;
        int temporaryScore = 0;
//        Up
        if (i >= 1 && pieces[i - 1][j] != null && pieces[i - 1][j].color == userColor) {

            for (int row = i - 1; row >= 0; row--) {
                if (pieces[row][j] == null) break;
                else if (pieces[row][j].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
//        Left
        if (j >= 1 && pieces[i][j - 1] != null && pieces[i][j - 1].color == userColor) {
            temporaryScore = 0;
            for (int column = j - 1; column >= 0; column--) {
                if (pieces[i][column] == null) break;
                else if (pieces[i][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
//        Down
        if (i < size - 1 && pieces[i + 1][j] != null && pieces[i + 1][j].color == userColor) {
            temporaryScore = 0;
            for (int row = i + 1; row < size; row++) {
                if (pieces[row][j] == null) break;
                else if (pieces[row][j].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
//        Right
        if (j < size - 1 && pieces[i][j + 1] != null && pieces[i][j + 1].color == userColor) {
            temporaryScore = 0;
            for (int column = j + 1; column < size; column++) {
                if (pieces[i][column] == null) break;
                else if (pieces[i][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
        //        Up-left
        if (i >= 1 && j >= 1 && pieces[i - 1][j - 1] != null && pieces[i - 1][j - 1].color == userColor) {
            temporaryScore = 0;
            for (int row = i - 1, column = j - 1; row >= 0 && column >= 0; row--, column--) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }


        //        Up-right
        if (i >= 1 && j < size - 1 && pieces[i - 1][j + 1] != null && pieces[i - 1][j + 1].color == userColor) {
            temporaryScore = 0;
            for (int row = i - 1, column = j + 1; row >= 0 && column < size; row--, column++) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
        //        Down-left
        if (i < size - 1 && j >= 1 && pieces[i + 1][j - 1] != null && pieces[i + 1][j - 1].color == userColor) {
            temporaryScore = 0;
            for (int row = i + 1, column = j - 1; row < size && column >= 0; row++, column--) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }


        //        Down-right
        if (i < size - 1 && j < size - 1 && pieces[i + 1][j + 1] != null && pieces[i + 1][j + 1].color == userColor) {
            temporaryScore = 0;
            for (int row = i + 1, column = j + 1; row < size && column < size; row++, column++) {
                if (pieces[row][column] == null) break;
                else if (pieces[row][column].color == userColor) temporaryScore++;
                else {
                    score += temporaryScore;
                    break;
                }
            }
        }
        return score;
    }

    void finish() {
        int blackCount = (userColor == Color.BLACK) ? userCount : comCount;
        int whiteCount = (userColor == Color.BLACK) ? comCount : userCount;
        System.out.println("Both players have no valid move.\n" +
                "Game over.\n" +
                "X : O = " + blackCount + " : " + whiteCount);
        if (blackCount > whiteCount) System.out.println("X player wins.");
        else System.out.println("O player wins.");
        System.exit(0);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (i == 0) {
                    if (j == 0) result.append("  ");
                    if (j != 0)
                        result.append((char) ('a' + j - 1)).append(" ");
                } else if (j == 0) {
                    result.append((char) ('a' + i - 1)).append(" ");
                } else if (pieces[i - 1][j - 1] == null) result.append(". ");
                else result.append(pieces[i - 1][j - 1]);
            }
            result.append("\n");
        }
        return result.toString();
    }
}
