/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    public Picture picture;
    private Color[][] rgb;
    private double[][] imgEnergy;
    private boolean transposed;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture is null!");
        Picture clonedPicture = new Picture(picture);
        updatePicture(clonedPicture);
    }

    private void updatePicture(Picture pic) {
        this.picture = pic;
        setTransposeAndCalculateEnergy(false);
        calculateEnergy();
    }

    // energy of pixel at column (or-width) x and row(or-height) y
    public double energy(int x, int y) {
        int height = rgb.length;
        int width = rgb[0].length;
        if (imgEnergy == null) calculateEnergy();
        if (x < 0 || x >= width)
            throw new IllegalArgumentException(x + "x - value beyond width" + width);
        if (y < 0 || y >= height)
            throw new IllegalArgumentException(y + "y - value beyond height" + height);
        return imgEnergy[y][x];
    }

    private void setTranspose(boolean value) {
        transposed = value;
    }

    private void updateTranspose(boolean value) {
        if (hasTransposed() == value && rgb != null) return;
        setTranspose(value);
        transpose();
    }

    private void setTransposeAndCalculateEnergy(boolean value) {
        if (rgb != null && hasTransposed() == value) return;
        updateTranspose(value);
        calculateEnergy();
    }

    /**
     * Transpose can make columns into rows and rows into column
     * We have two choices, 1st column could become first row, or last column can be first row
     * "last column becoming first row" also means first columns becomes last-row -- we have chosen
     * this approach
     *
     * @param isTransposed
     */
    private void transpose() {
        if (hasTransposed()) {
            rgb = new Color[picture.width()][picture.height()];
            for (int r = 0; r < picture().height(); r++) {
                for (int c = 0; c < picture().width(); c++) {
                    rgb[picture.width() - 1 - c][r] = picture.get(c, r);
                }
            }
        }
        else {
            rgb = new Color[picture.height()][picture.width()];
            for (int r = 0; r < picture().height(); r++) {
                for (int c = 0; c < picture().width(); c++) {
                    rgb[r][c] = picture.get(c, r);
                }
            }
        }
    }

    private boolean hasTransposed() {
        return transposed;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width (columns) of current picture
    public int width() {
        return picture.width();
    }

    // height (rows) of current picture
    public int height() {
        return picture.height();
    }

    private void calculateEnergy() {
        imgEnergy = new double[rgb.length][rgb[0].length];
        int height = rgb.length;
        int width = rgb[0].length;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    imgEnergy[row][col] = 1000;
                    continue;
                }
                double dx = Math.pow(rgb[row][col - 1].getRed() - rgb[row][col + 1].getRed(), 2)
                        + Math.pow(rgb[row][col - 1].getGreen() - rgb[row][col + 1].getGreen(), 2)
                        + Math.pow(rgb[row][col - 1].getBlue() - rgb[row][col + 1].getBlue(), 2);

                double dy = Math.pow(rgb[row - 1][col].getRed() - rgb[row + 1][col].getRed(), 2)
                        + Math
                        .pow(rgb[row - 1][col].getGreen() - rgb[row + 1][col].getGreen(), 2) + Math
                        .pow(rgb[row - 1][col].getBlue() - rgb[row + 1][col].getBlue(), 2);
                imgEnergy[row][col] = Math.sqrt(dx + dy);
            }
        }
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        setTransposeAndCalculateEnergy(true);
        int[] widths = findVerticalSeam();
        setTransposeAndCalculateEnergy(false);
        return java.util.stream.IntStream.rangeClosed(1, widths.length)
                                         .map(i -> widths[widths.length - i]).toArray();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        updateTranspose(true);
        validateSeam(seam);
        removeSeam(seam);
        updatedPicture(rgb);
        updateTranspose(false);
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int height = rgb.length;
        int width = rgb[0].length;

        double[][] distTo = new double[height][width];
        int[][] edgeTo = new int[height][width];

        for (int i = 0; i < height; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
        }
        Arrays.fill(distTo[0], energy(0, 0));

        for (int row = 0; row < height - 1; row++) {
            for (int col = 0; col < width - 1; col++) {
                for (int dx = -1; dx < 2; dx++) {
                    if (col + dx >= 0 && col + dx < width) {
                        // width for next height
                        if (distTo[row + 1][col + dx] > distTo[row][col] + energy(col + dx,
                                                                                  row + 1)) {
                            distTo[row + 1][col + dx] = distTo[row][col] + energy(col + dx,
                                                                                  row + 1);
                            edgeTo[row + 1][col + dx] = col;
                        }
                    }
                }
            }
        }

        double minWeight = Double.POSITIVE_INFINITY;
        int minWidth = 0;
        for (int c = 0; c < width; c++) {
            if (minWeight > distTo[height - 1][c]) {
                minWeight = distTo[height - 1][c];
                minWidth = c;
            }
        }
        java.util.List<Integer> pathTo = new java.util.ArrayList<Integer>(height());
        pathTo.add(0, minWidth);
        for (int i = height - 1; i > 0; i--) {
            minWidth = edgeTo[i][minWidth];
            pathTo.add(0, minWidth);
        }
        return pathTo.stream().mapToInt(Integer::intValue).toArray();
    }


    // remove vertical seam from current picture - seam HIW (Height indexed width)
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);
        removeSeam(seam);
        updatedPicture(rgb);
    }

    private void validateSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Seam is null");
        if (seam.length != rgb.length) throw new IllegalArgumentException(
                seam.length + " is not equal to expected vertical length " + rgb.length);
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > rgb[0].length - 1)
                throw new IllegalArgumentException(
                        seam[i] + " at " + i + " is not within the bound of 0 to " + (rgb[0].length
                                - 1));
            if (i < seam.length - 1 && Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new IllegalArgumentException(
                        seam[i] + " at " + i + " successvie seem must differ only by 1 or zero "
                                + seam[i + 1]);
        }
    }

    private void removeSeam(int[] seam) {
        for (int i = 0; i < seam.length; i++) {
            int w = hasTransposed() ? seam[seam.length - 1 - i] : seam[i];
            /*
                    [] l=13, w=7, 8,9,10,11,12, 13-8
             */
            System.arraycopy(rgb[i], w + 1, rgb[i], w, rgb[i].length - (w + 1));
            // for (int x = w; x < rgb[0].length - 1; x++) {
            //     rgb[i][x] = rgb[i][x + 1];
            // }
        }
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = Arrays.copyOfRange(rgb[i], 0, rgb[i].length - 1);
        }
    }

    private void updatedPicture(Color[][] newRgb) {
        Picture resizedPicture = null;
        if (hasTransposed()) {
            resizedPicture = new Picture(newRgb.length, newRgb[0].length);
            for (int r = 0; r < newRgb.length; r++) {
                for (int c = 0; c < newRgb[0].length; c++) {
                    resizedPicture.set(newRgb.length - 1 - r, c, newRgb[r][c]);
                }
            }
        }
        else {
            resizedPicture = new Picture(newRgb[0].length, newRgb.length);
            for (int h = 0; h < newRgb.length; h++) {
                for (int w = 0; w < newRgb[0].length; w++) {
                    resizedPicture.set(w, h, newRgb[h][w]);
                }
            }
        }
        updatePicture(resizedPicture);
    }

    public static void main(String[] args) {
        SeamCarver carver = new SeamCarver(new Picture(args[0]));
        System.out.println(Arrays.toString(carver.findVerticalSeam()));
        System.out.println(Arrays.toString(carver.findHorizontalSeam()));
        int[] seam = {  2, 3, 2, 3, 4, 4, 4, 5, 4, 6 };
        carver.removeHorizontalSeam(seam);
        seam = carver.findVerticalSeam();
        seam[(int) (Math.random() * seam.length)] = -1;
        carver.removeVerticalSeam(seam);
        seam = carver.findHorizontalSeam();
        seam[(int) (Math.random() * seam.length)] = -1;
        carver.removeHorizontalSeam(seam);
    }
}