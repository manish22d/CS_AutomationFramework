package com.cs.automationframework.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;



public class Command {

    /**
     * Execute a system command, wait for it to complete, log the output and return it as String
     * @param command
     * @return
     */
    public String execute (String command) {

        String response = "";

        try {

            // use the full path to SFDX (windows only)
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                String sfdxPath = RunParameters.PATH_TO_SFDX_INSTALLATION;
                if ((sfdxPath == null) || sfdxPath.contains(" ")){
                    throw new RuntimeException("\n \n ERROR - Path to SFDX installation (without spaces) is needed for Windows. \n 1. Please install SFDX in a directory so the full path to the SFDX installation contains no spaces (re-run the installer: sforce.co/2Sa3PEJ). \n 2. Provide the full path in RunParameters.FULL_PATH_TO_SFDX_INSTALLATION. \n GOOD: \"C:/my_new_dir/Salesforce_CLI/bin\" \n BAD: \"C:/Program Files/Salesforce CLI/bin\" \n");
                }
                else{
                    sfdxPath = sfdxPath.replace("\\", "/");
                    command = command.replace("sfdx ", sfdxPath + "/sfdx.cmd ");
                }
            }


            // if sfdx command does not contain -u param, assume we want to run as sysadmin
            if(command.contains(" force") && (!command.contains("-u"))){

                // get the correct sysadmin for the org
                String username = RunParameters.SYSADMIN_USERNAME;

                // authenticate the sysadmin
                SessionManager.getauthenticatedUrl(username);

                // append -u sysadmin to the command
                command = (command + " -u " + username);

            }


            // mac/linux cannot handle the nested quotes sometimes present in queries, so we generate an sh file an unescape the outer quotes
            if (!System.getProperty("os.name").toLowerCase().contains("win") && command.contains("\"")) {

                FileWriter.write("#!/bin/bash\n" + command, "command.sh");
                execute("chmod +x ./command.sh");
                command = "./command.sh";

            }



            // for debugging purposes
            System.out.println("executing command: " + command);

            // execute the command
            Process p = Runtime.getRuntime().exec(command);

            // read the command output from the process's input stream.
            BufferedReader commandOutputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = null;
            while ((output = commandOutputReader.readLine()) != null) {
                System.out.println(output);
                response += output;
            }

            String line;
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.out.println("ERROR: " + line);
            }

            errorReader.close();

        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("\n \n ERROR - The command failed to execute: \n" + command + " \n");
        }
        return response;
    }

}



