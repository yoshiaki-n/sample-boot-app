package com.example.samplebootapp.e2e;

import com.trivago.cluecumber.core.CluecumberCore;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;

public class CluecumberReportRunner {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: CluecumberReportRunner <jsonDirectory> <reportDirectory>");
            System.exit(1);
        }
        String jsonDirectory = args[0];
        String reportDirectory = args[1];

        try {
            new CluecumberCore.Builder().build().generateReports(jsonDirectory, reportDirectory);
            System.out.println("Cluecumber report generated in " + reportDirectory);
        } catch (CluecumberException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
