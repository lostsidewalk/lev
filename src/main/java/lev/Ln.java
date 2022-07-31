package lev;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@SuppressWarnings("unused")
public class Ln {
    public Ln() {
    }

    public static File getMyDocuments() {
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        return fw.getDefaultDirectory();
    }

    public static Object deepCopy(Object orig) {
        Object obj = null;

        try {
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
            obj = in.readObject();
        } catch (IOException | ClassNotFoundException var5) {
            var5.printStackTrace();
        }

        return obj;
    }

    private static String space(Boolean left, Boolean concat, int spaces, char c, String... input) {
        StringBuilder output = new StringBuilder();
        int len$ = input.length;

        for (String x : input) {
            output.append(x);
        }

        if (spaces < 4) {
            spaces = 4;
        }

        if (concat && output.length() > spaces) {
            output = new StringBuilder(output.substring(0, spaces / 2 - 2) + "..." + output.substring(output.length() - (spaces / 2 - 1)));
        }

        spaces -= output.length();

        for (int i = 0; i < spaces; ++i) {
            if (left) {
                output.insert(0, c);
            } else {
                output.append(c);
            }
        }

        return output.toString();
    }

    public static String spaceLeft(Boolean enforce, int spaces, char c, String... input) {
        return space(true, enforce, spaces, c, input);
    }

    public static String spaceRight(int spaces, char c, String... input) {
        return space(false, false, spaces, c, input);
    }

    public static String center(int spaces, char c, String... input) {
        StringBuilder output = new StringBuilder();
        int i = input.length;

        for (int i$ = 0; i$ < i; ++i$) {
            String x = input[i$];
            output.append(x);
        }

        int leftSpaces = (spaces - output.length()) / 2;

        for (i = 0; i < leftSpaces; ++i) {
            output.insert(0, c);
        }

        spaces -= output.length();

        for (i = 0; i < spaces; ++i) {
            output.append(c);
        }

        return output.toString();
    }

    public static String reverse(String input) {
        return String.copyValueOf(reverse(input.toCharArray()));
    }

    public static char[] reverse(char[] input) {
        char[] output = new char[input.length];

        for (int i = 0; i < input.length; ++i) {
            output[i] = input[input.length - i - 1];
        }

        return output;
    }

    public static int[] reverse(int[] input) {
        int[] output = new int[input.length];

        for (int i = 0; i < input.length; ++i) {
            output[i] = input[input.length - i - 1];
        }

        return output;
    }

    public static byte[] reverse(byte[] input) {
        byte[] output = new byte[input.length];

        for (int i = 0; i < input.length; ++i) {
            output[i] = input[input.length - i - 1];
        }

        return output;
    }

    public static String cleanLine(String line, String comment) {
        int commentIndex = line.indexOf(comment);
        if (-1 != commentIndex) {
            line = line.substring(0, commentIndex);
        }

        line = line.trim();
        return line;
    }

    public static boolean deleteDirectory(File directory) {
        if (directory == null) {
            return false;
        } else if (!directory.exists()) {
            return true;
        } else if (!directory.isDirectory()) {
            return false;
        } else {
            String[] list = directory.list();
            if (list != null) {
                for (String s : list) {
                    File entry = new File(directory, s);
                    if (entry.isDirectory()) {
                        if (!deleteDirectory(entry)) {
                            return false;
                        }
                    } else if (!entry.delete()) {
                        return false;
                    }
                }
            }

            return directory.delete();
        }
    }

    public static void makeDirs(File file) {
        makeDirs(file.getPath());
    }

    public static boolean equals(ArrayList<?> lhs, ArrayList<?> rhs, boolean ordered) {
        if (lhs == null && rhs == null) {
            return true;
        } else if (lhs != null && rhs != null) {
            if (lhs.size() != rhs.size()) {
                return false;
            } else {
                if (ordered) {
                    for (int i = 0; i < lhs.size(); ++i) {
                        if (!lhs.get(i).equals(rhs.get(i))) {
                            return false;
                        }
                    }
                } else {
                    ArrayList<?> tmp = new ArrayList<>(rhs);

                    for (Object o : lhs) {
                        tmp.remove(o);
                    }

                    return tmp.isEmpty();
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static void makeDirs(String file) {
        int index1 = file.lastIndexOf("/");
        int index2;
        if ((index2 = file.lastIndexOf("\\")) > index1) {
            index1 = index2;
        }

        if (index1 != -1) {
            file = file.substring(0, index1);
            File f = new File(file);
            if (!f.exists()) {
                //noinspection ResultOfMethodCallIgnored
                f.mkdirs();
            }
        }
    }

    public static boolean moveFile(File src, File dest, boolean eraseOldDirs) {
        makeDirs(dest);
        if (!src.renameTo(dest)) {
            return false;
        } else {
            if (eraseOldDirs) {
                //noinspection ConstantConditions
                while ((src = src.getParentFile()) != null && src.isDirectory() && src.listFiles().length == 0) {
                    //noinspection ResultOfMethodCallIgnored
                    src.delete();
                }
            }

            return true;
        }
    }

    public static ArrayList<File> generateFileList(File src, int minDepth, int maxDepth, boolean addDirs) {
        ArrayList<File> out = new ArrayList<>();
        if (src.isDirectory()) {
            File[] arr$ = src.listFiles();
            int len$ = arr$.length;

            for (File f : arr$) {
                if (minDepth <= 0 && (f.isFile() || addDirs)) {
                    out.add(f);
                }

                if (f.isDirectory() && maxDepth != 0) {
                    out.addAll(generateFileList(f, minDepth - 1, maxDepth - 1, addDirs));
                }
            }
        }

        return out;
    }

    public static ArrayList<File> generateFileList(File src, boolean addDirs) {
        return generateFileList(src, -1, -1, addDirs);
    }

    public static String arrayToString(int[] input) {
        StringBuilder output = new StringBuilder();

        for (int j : input) {
            output.append((char) j);
        }

        return output.toString();
    }

    public static String arrayToString(byte[] input) {
        StringBuilder output = new StringBuilder();

        for (byte b : input) {
            if (b != 0) {
                output.append((char) b);
            }
        }

        return output.toString();
    }

    public static boolean isFileCaseInsensitive(File test) {
        return !getFilepathCaseInsensitive(test).getPath().equals("");
    }

    public static File getFilepathCaseInsensitive(File test) {
        File dir;
        int index = test.getPath().lastIndexOf(92);
        if (index != -1) {
            dir = new File(test.getPath().substring(0, index));
        } else {
            dir = new File("");
        }

        if (dir.isDirectory()) {
            File[] arr$ = dir.listFiles();
            int len$ = arr$.length;

            for (File file : arr$) {
                if (test.getName().equalsIgnoreCase(file.getName())) {
                    return file;
                }
            }
        }

        return new File("");
    }

    public static int arrayToInt(int[] input) {
        return (int) arrayToLong(input);
    }

    public static long arrayToLong(int[] input) {
        int multiplier = 1;
        long output = 0L;

        for (int j : input) {
            output += (long) j * multiplier;
            multiplier *= 256;
        }

        return output;
    }

    public static int arrayToInt(byte[] input) {
        int out = 0;

        for (int i = input.length - 1; i >= 0; --i) {
            out |= input[i] & 255;
            if (i != 0) {
                out <<= 8;
            }
        }

        return out;
    }

    public static long arrayToLong(byte[] input) {
        long out = 0L;

        for (int i = input.length - 1; i >= 0; --i) {
            out |= input[i] & 255;
            if (i != 0) {
                out <<= 8;
            }
        }

        return out;
    }

    public static String arrayPrintInts(int[] input) {
        StringBuilder output = new StringBuilder();
        int len$ = input.length;

        for (int i : input) {
            output.append(i).append(" ");
        }

        return output.toString();
    }

    public static String printHex(long input) {
        return input < 16L ? "0" + Long.toHexString(input).toUpperCase() : Long.toHexString(input).toUpperCase();
    }

    public static String printHex(int input) {
        return printHex((long) input);
    }

    public static String printHex(int input, Boolean space, Boolean reverse, int minLength) {
        return printHex(toIntArray(input, minLength), space, reverse);
    }

    public static String printHex(byte[] input, Boolean space, Boolean reverse) {
        return printHex(toIntArray(input), space, reverse);
    }

    public static String printHex(Integer[] input, Boolean space, Boolean reverse) {
        return printHex(toIntArray(input), space, reverse);
    }

    public static String printHex(int[] input, Boolean space, Boolean reverse) {
        StringBuilder output = new StringBuilder();
        int len$ = input.length;

        for (int i : input) {
            if (reverse) {
                if (space) {
                    output.insert(0, " ");
                }

                output.insert(0, printHex(i));
            } else {
                output.append(printHex(i));
                if (space) {
                    output.append(" ");
                }
            }
        }

        return output.toString();
    }

    public static String prettyPrintHex(int input) {
        return prettyPrintHex((long) input);
    }

    public static String prettyPrintHex(long input) {
        return "{" + input + "->0x" + printHex(input) + "}";
    }

    public static String printDouble(double in, int length) {
        String out = String.valueOf(in);
        if (out.length() > length) {
            String suffix = "";
            if (out.contains("E")) {
                suffix = out.substring(out.indexOf(69));
            }

            return out.substring(0, length) + suffix;
        } else {
            return out;
        }
    }

    public static String getFileType(File f) {
        return f.getName().contains(".") ? f.getName().substring(f.getName().lastIndexOf(".") + 1) : "";
    }

    public static boolean isFileType(File f, String fileType) {
        return getFileType(f).equalsIgnoreCase(fileType);
    }

    public static String removeFromStr(String input, String remove) {
        String[] split = input.split(remove);
        StringBuilder output = new StringBuilder();
        int len$ = split.length;

        for (String s : split) {
            output.append(s);
        }

        return output.toString();
    }

    public static String insertInStr(String input, String insert, int location) {
        if (location <= 0) {
            return insert + input;
        } else {
            return location >= input.length() ? input + insert : input.substring(0, location - 1) + insert + input.substring(location);
        }
    }

    public static String convertBoolTo1(String input) {
        if (input.equals("true")) {
            return "1";
        } else {
            return input.equals("false") ? "0" : input;
        }
    }

    public static String convertBoolTo1(Boolean input) {
        return convertBoolTo1(input.toString());
    }

    public static Boolean toBool(String input) {
        return input.equals("1") || input.equals("true");
    }

    public static File setupFile(File f, Boolean delete) {
        File parent = new File(f.getParent());
        if (!parent.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            f.mkdirs();
        }

        if (delete && (f.isFile() || f.isDirectory())) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }

        return f;
    }

    public static File setupFile(String s, Boolean delete) {
        return setupFile(new File(s), delete);
    }

    public static void expandAll(JTree tree, boolean expand) {
        expandAll(tree, expand, 0, Integer.MAX_VALUE);
    }

    private static void expandAll(JTree tree, boolean expand, @SuppressWarnings("SameParameterValue") int depth, int maxDepth) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), expand, depth, maxDepth);
    }

    private static void expandAll(JTree tree, TreePath parent, boolean expand, int depth, int maxDepth) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            Enumeration<?> e = node.children();

            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand, depth + 1, maxDepth);
            }
        }

        if (expand) {
            if (depth < maxDepth) {
                tree.expandPath(parent);
            }
        } else {
            tree.collapsePath(parent);
        }

    }

    public static void expandToDepth(JTree tree, int depth) {
        expandAll(tree, false);
        expandAll(tree, true, 0, depth);
    }

    public static byte[] parseHexString(String hex, int min, boolean reverse) {
        return reverse ? reverse(parseHexString(hex, min)) : parseHexString(hex, min);
    }

    public static byte[] parseHexString(String hex) {
        return parseHexString(hex, 0);
    }

    public static byte[] parseHexString(String hex, int min) {
        byte[] tempOutput = new byte[1000];
        int counter = 0;
        String tmpHex = "";

        String next;
        for (Scanner scan = new Scanner(hex); scan.hasNext(); tmpHex = tmpHex + next) {
            next = scan.next();
            if (next.indexOf(120) != -1) {
                next = next.substring(next.indexOf(120) + 1);
            }
        }

        hex = tmpHex;
        if (tmpHex.length() % 2 == 1) {
            hex = "0" + tmpHex;
        }

        StringBuilder hexBuilder = new StringBuilder(hex);
        while (hexBuilder.length() < min * 2) {
            hexBuilder.insert(0, "00");
        }
        hex = hexBuilder.toString();

        for (int i = 0; i < hex.length(); i += 2) {
            tempOutput[counter++] = Integer.valueOf(hex.substring(i, i + 2), 16).byteValue();
        }

        byte[] output = new byte[counter];
        System.arraycopy(tempOutput, 0, output, 0, counter);
        return output;
    }

    public static int bToUInt(byte in) {
        return 255 & in;
    }

    public static int[] toIntArray(byte[] in) {
        int[] out = new int[in.length];

        for (int i = 0; i < in.length; ++i) {
            out[i] = bToUInt(in[i]);
        }

        return out;
    }

    private static int[] toIntArray(Integer[] in) {
        int[] out = new int[in.length];

        for (int i = 0; i < in.length; ++i) {
            out[i] = in[i];
        }

        return out;
    }

    public static int[] toIntArray(String input) {
        int[] output = new int[input.length()];

        for (int i = 0; i < input.length(); ++i) {
            output[i] = input.charAt(i);
        }

        return output;
    }

    public static int[] toIntArray(int input) {
        return toIntArray((long) input);
    }

    public static int[] toIntArray(int input, int minLength, int maxLength) {
        return toIntArray((long) input, minLength, maxLength);
    }

    public static int[] toIntArray(int input, int minLength) {
        return toIntArray((long) input, minLength, 0);
    }

    public static int[] toIntArray(long input) {
        return toIntArray(input, 0, 0);
    }

    public static int[] toIntArray(long input, int minLength, int maxLength) {
        if (maxLength == 0) {
            maxLength = 16;
        }

        int[] tmp = new int[maxLength];
        int counter = 0;

        for (int i = 0; i < tmp.length && input != 0L; ++i) {
            tmp[i] = (int) (input % 256L);
            input /= 256L;
            ++counter;
        }

        if (counter < minLength) {
            counter = minLength;
        } else if (counter == 0) {
            return new int[1];
        }

        int[] output = new int[counter];
        System.arraycopy(tmp, 0, output, 0, counter);
        return output;
    }

    public static byte[] toByteArray(int[] input) {
        byte[] out = new byte[input.length];

        for (int i = 0; i < input.length; ++i) {
            out[i] = (byte) input[i];
        }

        return out;
    }

    public static byte[] toByteArray(int input) {
        return new byte[]{(byte) input, (byte) (input >>> 8), (byte) (input >>> 16), (byte) (input >>> 24)};
    }

    public static byte[] toByteArray(int input, int size) {
        byte[] out = new byte[size];

        for (int i = 0; i < size && i < 4; ++i) {
            out[i] = (byte) (input >>> 8 * i);
        }

        return out;
    }

    public static byte[] toByteArray(int input, int minLength, int maxLength) {
        return toByteArray((long) input, minLength, maxLength);
    }

    public static byte[] toByteArray(long input, int minLength, int maxLength) {
        if (maxLength == 0) {
            maxLength = 16;
        }

        byte[] tmp = new byte[maxLength];
        int counter = 0;

        for (int i = 0; i < tmp.length && input != 0L; ++i) {
            tmp[i] = (byte) ((int) (input % 256L));
            input /= 256L;
            ++counter;
        }

        if (counter < minLength) {
            counter = minLength;
        } else if (counter == 0) {
            return new byte[0];
        }

        byte[] output = new byte[counter];
        System.arraycopy(tmp, 0, output, 0, counter);
        return output;
    }

    public static byte[] toByteArray(String input) {
        byte[] output = new byte[input.length()];

        for (int i = 0; i < input.length(); ++i) {
            output[i] = (byte) input.charAt(i);
        }

        return output;
    }

    public static String nanoTimeString(long nanoseconds) {
        int seconds = (int) ((double) nanoseconds * Math.pow(10.0, -3.0));
        int min = seconds / 60;
        seconds %= 50;
        return min + "m:" + seconds + "s";
    }

    public static String changeFileTypeTo(String input, String type) {
        return input.substring(0, input.lastIndexOf(".") + 1) + type;
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }

        return a;
    }

    public static int gcd(int... nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return gcd(nums[0], nums[1]);
        } else {
            int[] rest = Arrays.copyOfRange(nums, 1, nums.length);
            return gcd(nums[0], gcd(rest));
        }
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public static int lcmm(int... nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return lcm(nums[0], nums[1]);
        } else {
            int[] rest = Arrays.copyOfRange(nums, 1, nums.length);
            return lcm(nums[0], lcmm(rest));
        }
    }

    public static long toMB(long numBytes) {
        return numBytes / 1048576L;
    }

    public static long toKB(long numBytes) {
        return numBytes / 1024L;
    }

    public static File manualFindFile(String fileMessageToAskUserFor, File backupFileLocation) throws IOException {
        if (backupFileLocation.isFile()) {
            BufferedReader pluginLocation = new BufferedReader(new FileReader(backupFileLocation));
            File savedFile = new File(pluginLocation.readLine());
            if (savedFile.isFile()) {
                return savedFile;
            }
        }

        JOptionPane.showMessageDialog(null, "The application is having trouble locating: " + fileMessageToAskUserFor + "\n" + "Please locate this yourself.");
        JFileChooser fd = new JFileChooser(".");
        int returnVal = fd.showOpenDialog(null);
        File fileLocation = null;
        if (returnVal == 0) {
            fileLocation = fd.getSelectedFile();
            BufferedWriter pluginLocation = new BufferedWriter(new FileWriter(backupFileLocation));
            pluginLocation.write(fileLocation.getPath());
            pluginLocation.close();
        }

        return fileLocation;
    }

    public static File[] fileDialog() {
        JFileChooser fd = new JFileChooser(".");
        fd.setMultiSelectionEnabled(true);
        File[] fileLocation = new File[0];
        if (fd.showOpenDialog(null) == 0) {
            fileLocation = fd.getSelectedFiles();
        }

        return fileLocation;
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = (new FileInputStream(sourceFile)).getChannel();
            destination = (new FileOutputStream(destFile)).getChannel();
            destination.transferFrom(source, 0L, source.size());
        } finally {
            if (source != null) {
                source.close();
            }

            if (destination != null) {
                destination.close();
            }

        }
    }

    public static void copyFileToDir(File sourceFile, File destDir) throws IOException {
        File to = new File(destDir.getPath() + "\\" + sourceFile.getName());
        copyFile(sourceFile, to);
    }

    public static boolean validateCompare(File testFile, File keyFile, int numErrorsToPrint) {
        boolean print = numErrorsToPrint != 0;
        if (keyFile.isFile() && testFile.isFile()) {
            LInChannel keyIn = new LInChannel(keyFile);
            LInChannel testIn = new LInChannel(testFile);
            if (numErrorsToPrint == 0 && keyIn.available() != testIn.available()) {
                keyIn.close();
                testIn.close();
                return false;
            } else {
                byte[] keyArray = keyIn.extract(0, keyIn.available());
                byte[] testArray = testIn.extract(0, testIn.available());
                keyIn.close();
                testIn.close();
                boolean passed = true;

                for (int i = 0; i < keyArray.length && i < testArray.length; ++i) {
                    if (keyArray[i] != testArray[i]) {
                        if (print) {
                            System.out.println("Files differed at " + prettyPrintHex(i));
                        }

                        passed = false;
                        --numErrorsToPrint;
                        if (numErrorsToPrint == 0) {
                            break;
                        }
                    }
                }

                if (passed) {
                    if (print) {
                        System.out.println("Files matched.");
                    }

                    return true;
                } else {
                    if (print) {
                        System.out.println("Files did NOT match.");
                    }

                    return false;
                }
            }
        } else {
            if (print) {
                System.out.println("Validator could not locate both files (" + testFile + "," + keyFile + ")");
            }

            return false;
        }
    }

    public static boolean validateCompare(String testFile, String keyFile, int numErrorsToPrint) {
        return validateCompare(new File(testFile), new File(keyFile), numErrorsToPrint);
    }

    public static boolean isDescendant(TreePath parent, TreePath child) {
        int count1 = parent.getPathCount();
        int count2 = child.getPathCount();
        if (count1 <= count2) {
            return false;
        } else {
            while (count1 != count2) {
                parent = parent.getParentPath();
                --count1;
            }

            return parent.equals(child);
        }
    }

    public static String getExpansionState(JTree tree, int row) {
        TreePath rowPath = tree.getPathForRow(row);
        StringBuilder buf = new StringBuilder();
        int rowCount = tree.getRowCount();

        for (int i = row; i < rowCount; ++i) {
            TreePath path = tree.getPathForRow(i);
            if (i != row && !isDescendant(path, rowPath)) {
                break;
            }

            if (tree.isExpanded(path)) {
                buf.append(",").append(i - row);
            }
        }

        return buf.toString();
    }

    public static void restoreExpanstionState(JTree tree, int row, String expansionState) {
        StringTokenizer stok = new StringTokenizer(expansionState, ",");

        while (stok.hasMoreTokens()) {
            int token = row + Integer.parseInt(stok.nextToken());
            tree.expandRow(token);
        }

    }

    public static ArrayList<String> getClasses(File jarPath) throws IOException {
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jarPath));
        ArrayList<String> out = new ArrayList<>();

        JarEntry jarEntry;
        while ((jarEntry = jarFile.getNextJarEntry()) != null) {
            String name = jarEntry.getName();
            if (name.endsWith(".class") && !name.contains("$")) {
                name = name.substring(0, name.indexOf(".class"));
                name = name.replaceAll("/", ".");
                out.add(name);
            }
        }

        return out;
    }

    public static ArrayList<Class<?>> loadClasses(File jarPath, boolean skipBad) throws IOException, ClassNotFoundException {
        ArrayList<String> classPaths = getClasses(jarPath);
        ArrayList<Class<?>> out = new ArrayList<>(classPaths.size());
        ClassLoader loader = new URLClassLoader(new URL[]{jarPath.toURI().toURL()});

        for (String s : classPaths) {
            try {
                out.add(loader.loadClass(s));
            } catch (Throwable var8) {
                if (!skipBad) {
                    throw var8;
                }
            }
        }

        return out;
    }

    public static String convertStreamToStr(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];

            try (is) {
                Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            }

            return writer.toString();
        } else {
            return "";
        }
    }

    public static String toJsonPretty(JsonElement object, String... exclude) {
        String out = "{\n";
        ArrayList<String> excludeList = new ArrayList<>(Arrays.asList(exclude));
        out = out + toJsonPretty(object, 1, excludeList);
        return out + "\n}";
    }

    static String toJsonPretty(JsonElement object, int depth, ArrayList<String> exclude) {
        if (object.isJsonPrimitive()) {
            return object.getAsJsonPrimitive().toString();
        } else {
            StringBuilder out = new StringBuilder();
            StringBuilder tabs = new StringBuilder();

            tabs.append("\t".repeat(Math.max(0, depth)));

            boolean first;
            if (object.isJsonArray()) {
                Iterator<JsonElement> i$;
                JsonArray array = object.getAsJsonArray();
                out.append("\n").append(tabs).append("[");
                first = true;

                JsonElement entry;
                for (i$ = array.iterator(); i$.hasNext(); out.append(toJsonPretty(entry, depth + 1, exclude))) {
                    entry = i$.next();
                    if (first) {
                        first = false;
                    } else {
                        out.append(",");
                    }
                }

                out.append("\n").append(tabs).append("]");
            } else if (object.isJsonObject()) {
                Iterator<Map.Entry<String, JsonElement>> i$;
                Set<Map.Entry<String, JsonElement>> set = object.getAsJsonObject().entrySet();
                first = true;
                i$ = set.iterator();

                while (i$.hasNext()) {
                    Map.Entry<String, JsonElement> entry = i$.next();
                    if (!exclude.contains(entry.getKey())) {
                        if (!first) {
                            out.append(",\n");
                        } else {
                            first = false;
                        }

                        out.append(tabs).append("\"").append(entry.getKey()).append("\" : ");
                        out.append(toJsonPretty(entry.getValue(), depth + 1, exclude));
                    }
                }
            }

            return out.toString();
        }
    }

    public static ArrayList<String> loadFileToStrings(File f, boolean toUpper) throws IOException {
        ArrayList<String> out = new ArrayList<>();

        BufferedReader in;
        String line;
        for (in = new BufferedReader(new FileReader(f)); (line = in.readLine()) != null; out.add(line)) {
            if (toUpper) {
                line = line.toUpperCase();
            }
        }

        in.close();
        return out;
    }

    public static ArrayList<String> loadFileToStrings(String path, boolean toUpper) throws IOException {
        return loadFileToStrings(new File(path), toUpper);
    }

    public static void writeStringsToFile(String path, ArrayList<String> strs) throws IOException {
        File file = new File(path);
        makeDirs(file);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        for (String s : strs) {
            out.write(s + "\n");
        }

        out.close();
    }

    public static ArrayList<String> toUpper(ArrayList<String> in) {
        in.replaceAll(String::toUpperCase);

        return in;
    }

    public static String[] toUpper(String[] in) {
        for (int i = 0; i < in.length; ++i) {
            in[i] = in[i].toUpperCase();
        }

        return in;
    }

    public static String get(String[] arr, String target) {
        target = target.toUpperCase();
        int len$ = arr.length;

        for (String s : arr) {
            if (target.equals(s.toUpperCase())) {
                return s;
            }
        }

        return null;
    }

    public static boolean hasAnyKeywords(String target, ArrayList<String> keywords) {
        target = target.toUpperCase();
        Iterator<String> i$ = keywords.iterator();

        String s;
        do {
            if (!i$.hasNext()) {
                return false;
            }

            s = i$.next();
        } while (!target.contains(s.toUpperCase()));

        return true;
    }

    public static String standardizeFilePath(String filePath) {
        return filePath.replace("\\", "/");
    }

    public static int indexOfContains(ArrayList<String> list, String s) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).contains(s)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean contains(ArrayList<String> list, String s) {
        return -1 != indexOfContains(list, s);
    }

    public static boolean containsEqualsIgnoreCase(ArrayList<String> list, String s) {
        return -1 != indexOfIgnoreCase(list, s);
    }

    public static int indexOfIgnoreCase(ArrayList<String> list, String s) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equalsIgnoreCase(s)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean removeIgnoreCase(ArrayList<String> list, String s) {
        int index = indexOfIgnoreCase(list, s);
        if (index != -1) {
            list.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public static String getNAmount(int n, String s) {

        return String.valueOf(s).repeat(Math.max(0, n));
    }
}
