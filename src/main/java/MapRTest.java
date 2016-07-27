/* Copyright (c) 2009 & onwards. MapR Tech, Inc., All rights reserved */

//package com.mapr.fs;

import java.net.*;
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
        if (args.length != 2) {
            System.out.println("usage: MapRTest uri pathname");
            return;
        }

        // maprfs:/// -> uses the first entry in /opt/mapr/conf/mapr-clusters.conf
        // maprfs:///mapr/my.cluster.com/
        // /mapr/my.cluster.com/

        String uri = args[ac++];
        String dirname = args[ac++];

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(URI.create(uri), conf); // if wanting to use a different cluster
        // FileSystem fs = FileSystem.get(conf);

        Path dirpath = new Path( dirname + "/dir");
        Path wfilepath = new Path( dirname + "/file.w");
        //Path rfilepath = new Path( dirname + "/file.r");
        Path rfilepath = wfilepath;


        // try mkdir
        boolean res = fs.mkdirs( dirpath);
        if (!res) {
            System.out.println("mkdir failed, path: " + dirpath);
            return;
        }

        System.out.println( "mkdir( " + dirpath + ") went ok, now writing file");

        // create wfile
        FSDataOutputStream ostr = fs.create( wfilepath,
                true, // overwrite
                512, // buffersize
                (short) 1, // replication
                (long)(64*1024*1024) // chunksize
        );
        ostr.write(buf);
        ostr.close();

        System.out.println( "write( " + wfilepath + ") went ok");

        // read rfile
        System.out.println( "reading file: " + rfilepath);
        FSDataInputStream istr = fs.open( rfilepath);
        int bb = istr.readInt();
        istr.close();
        System.out.println( "Read ok");
    }
}
