package java_spc.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.FloatBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedMap;

import java_spc.util.Resource;


public class NewIO {
    private static final int BSIZE = 1024;

    public static void displayChannel(String fileName) throws IOException {
        FileChannel fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        fc.close();
        fc = new RandomAccessFile(fileName, "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("Some more ".getBytes()));
        fc.close();
        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        while (buff.hasRemaining()) {
            System.out.print((char) buff.get());
        }
    }

    public static void channelCopy(String fileRName, String fileWName) throws IOException {
        FileChannel in = new FileInputStream(fileRName).getChannel(),
                out = new FileOutputStream(fileWName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }

    public static void channelTransfer(String fileRName, String fileWName) throws IOException {
        FileChannel in = new FileInputStream(fileRName).getChannel(),
                out = new FileOutputStream(fileRName).getChannel();
        in.transferTo(0, in.size(), out);
    }

    public static void bufferToText(String fileName) throws IOException {
        FileChannel fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("Some Text 文本 ".getBytes()));
        fc.close();

        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoding use " + encoding + ": " + Charset.forName(encoding).decode(buffer));
        fc.close();

        fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("Some Text 文本 ".getBytes("UTF-16BE")));
        fc.close();

        fc = new FileInputStream(fileName).getChannel();
        buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
        fc.close();

        fc = new FileOutputStream(fileName).getChannel();
        buffer = ByteBuffer.allocate(100);
        buffer.asCharBuffer().put("Some Text 文本 ");
        fc.write(buffer);
        fc.close();

        fc = new FileInputStream(fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }

    public static void showCharSets() {
        SortedMap<String, Charset> charSets = Charset.availableCharsets();
        Iterator<String> iterator = charSets.keySet().iterator();
        while (iterator.hasNext()) {
            String csName = iterator.next();
            System.out.print(csName);
            Iterator<String> aliases = charSets.get(csName).aliases().iterator();
            if (aliases.hasNext()) {
                System.out.print(": ");
            }
            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if (aliases.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public static String getSystemEncoding() {
        return System.getProperty("file.encoding");
    }

    public static void getData() {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < bb.limit()) {
            if (bb.get() != 0) {
                System.out.println("nonzero");
            }
        }
        System.out.println("i=" + i);
        bb.rewind();
        //char
        bb.asCharBuffer().put("Howdy!");
        char c;
        while ((c = bb.getChar()) != 0) {
            System.out.print(c + " ");
        }
        System.out.println();
        bb.rewind();
        //short
        bb.asShortBuffer().put((short) 4771142);
        System.out.println(bb.getShort());
        bb.rewind();
        //int
        bb.asIntBuffer().put(99471112);
        System.out.println(bb.getInt());
        bb.rewind();
        //long
        bb.asLongBuffer().put(99471142);
        System.out.println(bb.getLong());
        bb.rewind();
        //float
        bb.asFloatBuffer().put(99471142);
        System.out.println(bb.getFloat());
        bb.rewind();
        //double
        bb.asDoubleBuffer().put(99471142);
        System.out.println(bb.getDouble());
        bb.rewind();
    }

    public static void intBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = bb.asIntBuffer();
        ib.put(new int[]{11, 47, 65, 34, 89, 14});
        System.out.println(ib.get(3));
        ib.put(3, 1811);
        ib.flip();
        while (ib.hasRemaining()) {
            int i = ib.get();
            System.out.println(i);
        }
    }

    public static void viewBuffer() {
        //byte
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
        bb.rewind();
        System.out.print("Byte Buffer ");
        while (bb.hasRemaining())
            System.out.print(bb.position() + " -> " + bb.get() + ", ");
        System.out.println();
        //char
        CharBuffer cb = ((ByteBuffer) bb.rewind()).asCharBuffer();
        System.out.print("Char Buffer ");
        while (cb.hasRemaining())
            System.out.print(cb.position() + " -> " + cb.get() + ",");
        System.out.println();
        //short
        ShortBuffer sb = ((ByteBuffer) bb.rewind()).asShortBuffer();
        System.out.print("Short Buffer ");
        while (sb.hasRemaining())
            System.out.print(sb.position() + " -> " + sb.get() + ",");
        System.out.println();
        //int
        IntBuffer ib = ((ByteBuffer) bb.rewind()).asIntBuffer();
        System.out.print("Int Buffer ");
        while (ib.hasRemaining())
            System.out.print(ib.position() + " -> " + ib.get() + ",");
        System.out.println();
        //long
        LongBuffer lb = ((ByteBuffer) bb.rewind()).asLongBuffer();
        System.out.print("Long Buffer ");
        while (lb.hasRemaining())
            System.out.print(lb.position() + " -> " + lb.get() + ",");
        System.out.println();
        //float
        FloatBuffer fb = ((ByteBuffer) bb.rewind()).asFloatBuffer();
        System.out.print("Float Buffer ");
        while (fb.hasRemaining())
            System.out.print(fb.position() + " -> " + fb.get() + ",");
        System.out.println();
        //double
        DoubleBuffer db = ((ByteBuffer) bb.rewind()).asDoubleBuffer();
        System.out.print("Double Buffer ");
        while (db.hasRemaining())
            System.out.print(db.position() + " -> " + db.get() + ",");
        System.out.println();
    }

    public static void endians() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
        bb.rewind();
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
        bb.rewind();
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
    }

    public static void scramble(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            buffer.mark();
            char c1 = buffer.get();
            char c2 = buffer.get();
            buffer.reset();
            buffer.put(c2).put(c1);
        }
    }

    public static void swapNearCharInString(String s) {
        char[] data = s.toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        System.out.println(cb.rewind());
        scramble(cb);
        System.out.println(cb.rewind());
        scramble(cb);
        System.out.println(cb.rewind());
    }

    public static void largeMapper() throws IOException {
        int length = 0x8FFFFFF; //128MB
        MappedByteBuffer out = new RandomAccessFile(Resource.path("file/sing.out"), "rw").getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        System.out.println("Finish writing");
        for (int i = length / 2; i < length / 2 + 6; i++) {
            System.out.print((char) out.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
//        displayChannel(Resource.pathToSource("file/sing.txt"));
        channelCopy(Resource.pathToSource("file/sing.txt"), Resource.pathToSource("file/sing.out"));
//        channelTransfer(Resource.pathToSource("file/sing.txt"), Resource.pathToSource("file/sing.out"));
//        bufferToText(Resource.pathToSource("file/data.txt"));
//        showCharSets();
//        System.out.println(getSystemEncoding());
//        getData();
//        intBuffer();
//        viewBuffer();
//        endians();
//        swapNearCharInString("What is More");
//        largeMapper();
    }
}