package com.tequeno.sudoku.test;

public class Test {

    String lastStr = "";

    private final static String DOM_HEADER = "<<<length=";
    private final static String DOM_TAIL = ">>>";

    public static void main(String[] args) {
//        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
//        Long val = 34L;
//        Calendar c = Calendar.getInstance();
//        System.out.println(sdf.format(c.getTime()));
//        c.add(Calendar.DAY_OF_MONTH, val.intValue() - 2 * val.intValue());
//        System.out.println(sdf.format(c.getTime()));
//
//        Position position = Position.P01;
//        System.out.println(position.ordinal());
//        System.out.println(position.name());
//        Position position1 = Position.valueOf("P0101");
//        System.out.println(position1.ordinal());


        String dom = "<<<length=92>>><event>\n" +
                "\t<cmdType>register</cmdType>\n" +
                "\t<ext>1005@1</ext>\n" +
                "\t<userid>admin980</userid>\n" +
                "</event>";
        int start = dom.indexOf(DOM_HEADER);
        int end = dom.indexOf(DOM_TAIL);
        String lengthString = dom.substring(start + DOM_HEADER.length(), end);
        System.out.println(lengthString);
        System.out.println(dom.length() - DOM_HEADER.length() - DOM_TAIL.length() - lengthString.length());
    }
}
