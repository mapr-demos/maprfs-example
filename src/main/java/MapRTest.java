/* Copyright (c) 2009 & onwards. MapR Tech, Inc., All rights reserved */

//package com.mapr.fs;

import java.io.PrintStream;
import java.net.*;

import com.mapr.fs.clicommands.MapRCliCommands;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;

/**
 * Assumes mapr installed in /opt/mapr
 *
 * Compilation:
 * javac -cp $(hadoop classpath) MapRTest.java
 *
 * Run:
 * java -cp .:$(hadoop classpath) MapRTest /test
 */
public class MapRTest
{
    public static void main(String args[]) throws Exception {
        byte buf[] = new byte[ 65*1024];
        int ac = 0;
        if (args.length != 1) {
            System.out.println("usage: MapRTest pathname");
            return;
        }

        // maprfs:/// -> uses the first entry in /opt/mapr/conf/mapr-clusters.conf
        // maprfs:///mapr/my.cluster.com/
        // mapr/my.cluster.com/

        // String uri = "maprfs:///";
        String dirname = args[ac++];

        Configuration conf = new Configuration();

        //FileSystem fs = FileSystem.get(URI.create(uri), conf); // if wanting to use a different cluster
        FileSystem fs = FileSystem.get(conf);

//        Path dirpath = new Path( dirname + "/dir");
//        Path wfilepath = new Path( dirname + "/file.w");
//        //Path rfilepath = new Path( dirname + "/file.r");
//        Path rfilepath = wfilepath;
//
//
//        // try mkdir
//        boolean res = fs.mkdirs( dirpath);
//        if (!res) {
//            System.out.println("mkdir failed, path: " + dirpath);
//            return;
//        }
//
//        System.out.println( "mkdir( " + dirpath + ") went ok, now writing file");
//
//        // create wfile
//        FSDataOutputStream ostr = fs.create( wfilepath,
//                true, // overwrite
//                512, // buffersize
//                (short) 1, // replication
//                (long)(64*1024*1024) // chunksize
//        );
//        ostr.write(buf);
//        ostr.close();
//
//        System.out.println( "write( " + wfilepath + ") went ok");
//
//        // read rfile
//        System.out.println( "reading file: " + rfilepath);
//        FSDataInputStream istr = fs.open( rfilepath);
//        int bb = istr.readInt();
//        istr.close();
//        System.out.println( "Read ok");
        MapRCliCommands mprcmd = new MapRCliCommands(conf);
        //String cmds[] = {"-ls","/"};
       // mprcmd.run(cmds);
        String cmds2[] = {"-lsfid","2049.34.131180"};

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        mprcmd.run(cmds2);

        System.out.flush();
        System.setOut(old);
        // Show what happened
        System.out.println("Here is the string: " + baos.toString());

    }
}