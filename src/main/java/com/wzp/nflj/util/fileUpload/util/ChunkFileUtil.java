package com.wzp.nflj.util.fileUpload.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;


/**
 * @Author: zp.wei
 * @DATE: 2020/8/7 10:16
 */
public class ChunkFileUtil {

    public static final String Chunk_Delimiter = "-";
    private static byte[] _byte = new byte[1024];


    public static List<File> readChunks(File chunkDir) {
        // 读取分片文件
        File[] chunks = null;
        if (chunkDir.exists()) {
            chunks = chunkDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return false;
                    }
                    return true;
                }
            });
        }
        // 分片文件排序
        List<File> chunkList = null;
        if (chunks != null && chunks.length > 0) {
            chunkList = Arrays.asList(chunks);
            Collections.sort(chunkList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        return chunkList;
    }


    public static void randomAccessFile(File in, File out, Long seek) throws IOException {
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;
        try {
            // 以读写的方式打开目标文件
            raFile = new RandomAccessFile(out, "rw");
            raFile.seek(seek);
            inputStream = new BufferedInputStream(new FileInputStream(in));
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                raFile.write(buf, 0, length);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (raFile != null) {
                    raFile.close();
                }
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }


    /**
     * 合并文件
     *
     * @param fpaths     要合并的文件路径集合，顺序
     * @param resultPath 合并后的文件路径
     * @return
     */
    public static boolean mergeFiles(List<String> fpaths, String resultPath) throws Exception {
        if (fpaths == null || fpaths.isEmpty()) {
            return false;
        }
        if (fpaths.size() == 1) {
            return new File(fpaths.get(0)).renameTo(new File(resultPath));
        }
        File[] files = new File[fpaths.size()];
        for (int i = 0; i < fpaths.size(); i++) {
            files[i] = new File(fpaths.get(i));
            if (!files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }
        File resultFile = new File(resultPath);
        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.size(); i++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return true;
    }

    /**
     * 获取文件集合
     *
     * @param path     文件夹路径
     * @param fileName 文件名+后缀
     * @return
     */
    public static ArrayList<String> getAllFileName(String path, String fileName) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        ArrayList<String> fileNameList = new ArrayList<>();
        for (int i = 0; i < tempList.length; i++) {
            fileNameList.add(path + File.separator + i + fileName);
        }
        return fileNameList;
    }


    /**
     * 文件重命名
     *
     * @param newFileName
     * @param oldFileName
     */
    public static void renameFile(String newFileName, String oldFileName) {
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.renameTo(newFile);
        }
    }
}

