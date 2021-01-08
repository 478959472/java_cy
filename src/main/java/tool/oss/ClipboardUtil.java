package tool.oss;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author montnets
 */
public class ClipboardUtil {
    public static void main(String[] args) throws Exception {
        System.err.println("usage: java clipimg [filename]");
        String outputfile = "/temp/1.png";
        if (args.length > 0) {
            outputfile = args[0];
        }
        copyTo(outputfile);
    }

    public static void setClipboard(String content){
        if(content == null || "".equals(content)){
            return;
        }
        StringSelection stringSelection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static  File copyTo(String filename) throws Exception {
        Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (content == null) {
            System.err.println("error: nothing found in clipboard");
            return null;
        }
        if (!content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            System.err.println("error: no image found in clipbaord");
            return null;
        }
        BufferedImage img = (BufferedImage) content.getTransferData(DataFlavor.imageFlavor);
        String ext = ext(filename);
        if (ext == null) {
            ext = "png";
            filename += "." + ext;
        }
        File outfile = new File(filename);
        ImageIO.write(img, ext, outfile);
        System.err.println("image copied to: " + outfile.getAbsolutePath());
        return outfile;
    }

    static String ext(String filename) {
        int pos = filename.lastIndexOf('.') + 1;
        if (pos == 0 || pos >= filename.length()) {
            return null;
        }
        return filename.substring(pos);
    }
}
