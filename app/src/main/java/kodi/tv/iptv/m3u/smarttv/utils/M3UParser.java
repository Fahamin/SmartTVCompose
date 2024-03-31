package kodi.tv.iptv.m3u.smarttv.utils;

import android.util.Log;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import kodi.tv.iptv.m3u.smarttv.model.ChannelModel;

public class M3UParser {
    public ArrayList<ChannelModel> parse(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            boolean z = false;
            ArrayList<ChannelModel> arrayList = null;
            while (!z) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String trim = readLine.trim();
                if (startsWithBom(trim)) {
                    trim = trim.substring(1);
                }
                if (trim.toLowerCase().startsWith("#extm3u")) {
                    arrayList = new ArrayList<>();
                    z = true;
                }
            }
            String str2 = "";
            while (true) {
                String readLine2 = bufferedReader.readLine();
                if (readLine2 != null) {
                    String trim2 = readLine2.trim();
                    if (z) {
                        if (trim2.toLowerCase().startsWith("#extinf:") && trim2.length() > 8) {
                            str2 = trim2.substring(8).trim();
                        } else if (!trim2.startsWith("#") && trim2.trim().length() > 5) {
                            ChannelModel ChannelModel = new ChannelModel();
                            ChannelModel.setPath(trim2);
                            HashMap<String, String> parseOptions = parseOptions(str2);
                            if (parseOptions.containsKey("title")) {
                                ChannelModel.setTitle(parseOptions.get("title"));
                            }
                            if (parseOptions.containsKey("logo")) {
                                ChannelModel.setLogoUrl(parseOptions.get("logo"));
                                System.out.println("logo exist");
                            } else if (parseOptions.containsKey("tvg-logo")) {
                                ChannelModel.setLogoUrl(parseOptions.get("tvg-logo"));
                                System.out.println("logo exist");
                                Log.i("titlelog", parseOptions.get("tvg-logo"));
                            } else if (parseOptions.containsKey("tv-logo")) {
                                ChannelModel.setLogoUrl(parseOptions.get("tv-logo"));
                                System.out.println("title exist");
                            }
                            if (parseOptions.containsKey("mime-type")) {
                                ChannelModel.setMime_type(parseOptions.get("mime-type"));
                            }
                            arrayList.add(ChannelModel);
                            str2 = "";
                        }
                    }
                } else {
                    bufferedReader.close();
                    return arrayList;
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private HashMap<String, String> parseOptions(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] split = str.split("[,]");
        if (split.length >= 2) {
            boolean z = false;
            String trim = split[0].trim();
            hashMap.put("title", split[1].trim());
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            boolean z2 = false;
            boolean z3 = false;
            for (int i = 0; i < trim.length(); i++) {
                char charAt = trim.charAt(i);
                if (charAt == ' ' && !z3) {
                    if (z2) {
                        if ((sb.length() > 0) && (sb2.length() > 0)) {
                            hashMap.put(sb.toString(), sb2.toString());
                        }
                        z2 = false;
                    }
                    sb = new StringBuilder();
                    sb2 = new StringBuilder();
                } else if (charAt == '\"') {
                    z3 = !z3;
                } else if (charAt == '=') {
                    z2 = true;
                } else if (z2) {
                    sb2.append(charAt);
                } else {
                    sb.append(charAt);
                }
            }
            if (z2) {
                boolean z4 = sb.length() > 0;
                if (sb2.length() > 0) {
                    z = true;
                }
                if (z4 && z) {
                    hashMap.put(sb.toString(), sb2.toString());
                }
            }
        }
        return hashMap;
    }

    private boolean startsWithBom(String str) {
        if (str.length() <= 0) {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt == 65279) {
            System.out.println("this file starts with a BOM");
            return true;
        }
        PrintStream printStream = System.out;
        printStream.println("file starts with " + charAt);
        return false;
    }
}
