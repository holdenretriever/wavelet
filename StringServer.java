import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String r = new String("");

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return r;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    r += parameters[1] + "\n";
                    return String.format("concatenated %s to string", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String r2 = new String();
                if (parameters[0].equals("s")) {
                    String[] strs = r.split("\n");
                    for (int i=0; i<strs.length; i++){
                        if (strs[i].contains(parameters[1]))
                            r2 += strs[i] + "\n";
                    }
                    return r2;

                }
            }
            return "404 Not Found!";
        }
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
