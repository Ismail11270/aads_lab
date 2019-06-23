package com.zoobie.aads.dynamic;




class DynamicProgramming {

    private static int[][] c;
    private static String[][] b;

    public static void main(String[] agrs) {

        String X = "ABCBDAB";
        String Y = "BDCABA";
        System.out.println("Length of LCS is" + " " + LCSLength(X, Y, X.length(), Y.length()));
        printLSC(X,X.length(),Y.length());
        System.out.println();
        System.out.println("Edit distance " + editDist("SUNDAY", "SATURDAY", 1, 1, 1));
    }

    public static int editDist(String x, String y, int cd, int ci, int cr){
        int n = y.length();
        int m = x.length();

        c = new int[m+1][n+1];
        b = new String[m+1][n+1];
        for(int i = 0; i < m; i++){
            c[i][0] = i * cd;
            b[i][0] = "up";
        }
        for(int i = 0; i < n; i++){
            c[0][i] = i * ci;
            b[0][i] = "left";
        }

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){

                int insCost = c[i][j-1] + ci;
                int delCost = c[i-1][j] + cd;
                int replCost = c[i-1][j-1] + (x.charAt(i-1) == y.charAt(j-1) ? 0 : 1);

                c[i][j] = insCost;
                b[i][j] = "left";
                if(c[i][j] > delCost){
                    c[i][j] = delCost;
                    b[i][j] = "up";
                }
                if(c[i][j] > replCost) {
                    c[i][j] = replCost;
                    b[i][j] = "upleft";
                }
            }
        }
        return c[m][n];
    }



    public static int LCSLength(String X, String Y, int m, int n) {
        c = new int[m + 1][n + 1];
        b = new String[m + 1][n + 1];
        for (int i = 0; i < m; i++) c[i][0] = 0;
        for (int i = 0; i < n; i++) c[0][i] = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "upleft";
                } else {
                    if (c[i - 1][j] >= c[i][j - 1]) {
                        c[i][j] = c[i - 1][j];
                        b[i][j] = "up";
                    } else {
                        c[i][j] = c[i][j - 1];
                        b[i][j] = "left";
                    }
                }
            }
        }
        return c[m][n];
    }

    public static void printLSC(String X, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (b[i][j].equals("upleft")) {
            printLSC(X, i - 1, j - 1);
            System.out.print(X.charAt(i - 1));
        } else {
            if (b[i][j].equals("up")) {
                printLSC(X, i - 1, j);
            } else {
                printLSC(X, i, j - 1);
            }
        }
    }


}