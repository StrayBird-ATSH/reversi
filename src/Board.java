import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Board {
    int size;
    private Piece[][] pieces;
    int comCount = 2;
    int userCount = 2;
    char computerPieceName = '0';
    private Color userColor;

    Board(int size) {
        this.size = size;
        pieces = new Piece[size][size];
    }

    Color getUserColor() {
        return userColor;
    }

    void setUserColor(Color userColor) {
        if (userColor == Color.BLACK)
            computerPieceName = 'O';
        else computerPieceName = 'X';
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

    void placeOpt() {
        int highestScore = 0;
        int currentScore;
        int optimalI = 0, optimalJ = 0;
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
        else if (whiteCount > blackCount) System.out.println("O player wins.");
        else System.out.println("Draw!");
        long currentTime = System.currentTimeMillis();
        int timeElapsed = (int) (currentTime - Main.time);
        writeLog(Main.time, timeElapsed, false);
        System.exit(0);
    }

    void writeLog(long startTime, int duration, boolean isGiveUp) {
        Date date = new Date(startTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String log = dateFormat.format(date);
        log += ("," + duration / 1000 + ",");
        log += (size + "*" + size + ",");
        if (userColor == Color.BLACK) log += "computer,human,";
        else log += "human,computer,";
        if (isGiveUp) log += "Human gave up." + System.lineSeparator();
        else if (userColor == Color.BLACK) log += (comCount + " to " + userCount + System.lineSeparator());
        else log += (userCount + " to " + comCount + System.lineSeparator());
        File file = new File("reversi.csv");
        try (FileWriter fileWriter = new FileWriter(file, Charset.forName("UTF-8"), true)) {
            fileWriter.append(log);
        } catch (IOException e) {
            System.out.println("Unexpected IO exception, the process will terminate");
            e.printStackTrace();
        }
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
