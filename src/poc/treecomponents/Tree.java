package poc.treecomponents;

import java.util.ListIterator;

public class Tree {

    private BaseNode rootNode;

    public Tree() {
        rootNode = null;
    }

    public Tree(DimensionNode dn) {
        rootNode = dn;
    }

    public static void main(String[] args) throws Exception {
        //DimensionNode ( int stage, String dimName, double totalMetric)
        //ValueNode (String valName, int stage, double totalAgg, String full)

        Tree t = new Tree();

        DimensionNode si = new DimensionNode(1, "Advocate R Sankar Iyer", 1897);
        t.addNodeToChild(si, null);
        ValueNode v1 = new ValueNode("S Ranganathan", 1, 1925, "");
        si.addValNode(v1);
        //t.addNodeToChild(si, null);

        DimensionNode leela = new DimensionNode(2, "Leela", 1959);
        t.addNodeToChild(leela, v1);
        //v1.addDimChild(leela);
        leela.addValNode(new ValueNode("Priya", 2, 1979, ""));
        leela.addValNode(new ValueNode("Praveen", 2, 1981, ""));
        DimensionNode sekhar = new DimensionNode(2, "Sekhar", 1962);
        //v1.addDimChild(sekhar);
        t.addNodeToChild(sekhar, v1);
        sekhar.addValNode(new ValueNode("Karthik", 2, 1993, ""));
        sekhar.addValNode(new ValueNode("Rithika", 2, 1997, ""));
        DimensionNode rekha = new DimensionNode(2, "Rekha", 1966);
        //v1.addDimChild(rekha);
        t.addNodeToChild(rekha, v1);
        rekha.addValNode(new ValueNode("Kannan", 2, 1981, ""));

        ValueNode v2 = new ValueNode("S Krishnambal", 1, 1927, "");
        si.addValNode(v2);
        DimensionNode jayaram = new DimensionNode(2, "Jayaram", 1946);
        //v2.addDimChild(jayaram);
        t.addNodeToChild(jayaram, v2);
        jayaram.addValNode(new ValueNode("Prashanth", 2, 1985, ""));

        ValueNode v3 = new ValueNode("S Vishalakshi", 1, 1932, "");
        si.addValNode(v3);
        DimensionNode gokul = new DimensionNode(2, "Gokul", 1958);
        //v3.addDimChild(gokul);
        t.addNodeToChild(gokul, v3);
        gokul.addValNode(new ValueNode("Ankita", 2, 1986, ""));
        gokul.addValNode(new ValueNode("Ritika", 2, 1994, ""));

        DimensionNode arun = new DimensionNode(2, "Arun", 1963);
        //v3.addDimChild(arun);
        t.addNodeToChild(arun, v3);
        arun.addValNode(new ValueNode("Neha", 2, 1993, ""));
        arun.addValNode(new ValueNode("Rhea", 2, 1997, ""));


        ValueNode v4 = new ValueNode("S Varadarajan", 1, 1934, "");
        si.addValNode(v4);
        DimensionNode vijay = new DimensionNode(2, "Vijay", 1973);
        //v4.addDimChild(vijay);
        t.addNodeToChild(vijay, v4);
        vijay.addValNode(new ValueNode("Kavya", 2, 2003, ""));
        vijay.addValNode(new ValueNode("Keertana", 2, 2008, ""));
        DimensionNode nandini = new DimensionNode(1, "Nandini", 1973);
        //v4.addDimChild(nandini);
        t.addNodeToChild(nandini, v4);

        ValueNode v5 = new ValueNode("S Chellamma", 1, 1938, "");
        si.addValNode(v5);


        //t.traverseDimensionLink(si, "");
        System.out.println(t.traverseDimensionJSon(si));
    }

    public void addNodeToChild(DimensionNode child, ValueNode parent) throws Exception {
        if (rootNode == null) {
            rootNode = child;
            return;
        }
        if (parent == null) {
            throw new Exception("The parent Value JacksonGenExample is null");
        }
        parent.addDimChild(child);
    }

    public void traverseDimensionLink(DimensionNode dn, String ancestry) {
        if (dn.getValChildren().isEmpty()) {
            System.out.println(ancestry + dn.getDimensionName());
        }
        ListIterator<ValueNode> listIterator = dn.getValChildren().listIterator();
        while (listIterator.hasNext()) {
            ValueNode vn = listIterator.next();
            StringBuilder sb = new StringBuilder();
            sb.append(ancestry);
            //sb.append("->");
            sb.append(dn.getDimensionName());
            sb.append("->");
            traverseValueLink(vn, sb.toString());
        }
    }

    public void traverseValueLink(ValueNode vn, String ancestry) {
        if (vn.getDimChildren().isEmpty()) {
            System.out.println(ancestry + vn.getValueName());
        }
        ListIterator<DimensionNode> listIterator = vn.getDimChildren().listIterator();
        while (listIterator.hasNext()) {
            DimensionNode dn = listIterator.next();
            StringBuilder sb = new StringBuilder();
            sb.append(ancestry);
            //sb.append("->");
            sb.append(vn.getValueName());
            sb.append("->");
            traverseDimensionLink(dn, sb.toString());

        }
    }

    public String traverseDimensionJSon(DimensionNode dn) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("name : \"");
        sb.append(dn.getDimensionName());
        sb.append("\"");
        if (dn.getValChildren().isEmpty()) {
            sb.append("}");
            return sb.toString();
        }
        sb.append(", values :");
        sb.append("[");
        ListIterator<ValueNode> listIterator = dn.getValChildren().listIterator();
        while (listIterator.hasNext()) {
            ValueNode vn = listIterator.next();
            //sb.append("{");
            sb.append(traverseValueJSon(vn));
            sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    public String traverseValueJSon(ValueNode vn) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("name : \"");
        sb.append(vn.getValueName());
        sb.append("\"");
        if (vn.getDimChildren().isEmpty()) {
            sb.append("}");
            return sb.toString();
        }
        sb.append(",dimensions :");
        sb.append("[");
        ListIterator<DimensionNode> listIterator = vn.getDimChildren().listIterator();
        while (listIterator.hasNext()) {
            DimensionNode dn = listIterator.next();
            //sb.append("{");
            sb.append(traverseDimensionJSon(dn));
            sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }


}
