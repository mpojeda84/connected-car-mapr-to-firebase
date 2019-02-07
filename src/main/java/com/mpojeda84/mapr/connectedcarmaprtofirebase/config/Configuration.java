package com.mpojeda84.mapr.connectedcarmaprtofirebase.config;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Configuration {

    public static Options generateOptions() {
        final Option table = Option.builder("t")
                .required()
                .longOpt("table")
                .hasArg()
                .desc("Table in Mapr")
                .build();
        final Option firebase = Option.builder("f")
                .required()
                .longOpt("firebase")
                .hasArg()
                .desc("Firebase URL") // ex: https://connected-car-3f879.firebaseio.com
                .build();

        final Option delay = Option.builder("d")
                .required()
                .longOpt("delay")
                .hasArg()
                .desc("Delay") // ex: https://connected-car-3f879.firebaseio.com
                .build();

        final Option messagesTable = Option.builder("m")
                .required()
                .longOpt("messages")
                .hasArg()
                .desc("Messages") // ex: https://connected-car-3f879.firebaseio.com
                .build();


        final Options options = new Options();
        options.addOption(table);
        options.addOption(firebase);
        options.addOption(delay);
        options.addOption(messagesTable);
        return options;
    }
}
