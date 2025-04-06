import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Automata{

    static class Edge{
        private final State from;
        private final List<String> label;
        private final State to;

        public Edge(State from, State to, String[] label){
            this.from = from;
            this.to = to;
            this.label = new ArrayList<>();
            Collections.addAll(this.label, label);
        }

        public void updateLabel(String[] label){
            for(String s : label){
                if (!this.label.contains(s)){
                    this.label.add(s);
                }
            }
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(from.getId()).append(" -- { ");

            for (int i = 0; i < this.label.size(); i++){
                sb.append(this.label.get(i));
                if (i < this.label.size() - 1) sb.append(", ");
            }
            sb.append(" } --> ").append(to.getId());
            return sb.toString();
        }
    }

    static class State{
        private final String id;
        private final ArrayList<Edge> edges;

        public State(String id){
            this.id = id;
            this.edges = new ArrayList<>();
        }

        public String getId(){
            return this.id;
        }

        public Edge findEdge(State s){
            for (Edge edge: this.edges){
                if ( edge.from == s || edge.to  == s) return edge;
            }
            return null;
        }

        public void createEdge(State dest, String[] label){

            Edge srcEdge = findEdge(this);
            Edge destEdge = findEdge(dest);
            if (srcEdge != null && destEdge != null){
                srcEdge.updateLabel(label);
                return;
            }
            this.edges.add(new Edge(this, dest, label));
        }
    }

    final State initialState;
    private final List<State> states;
    public int size = 0;

    public Automata(String name){
        this.initialState = new State(name);
        this.states = new ArrayList<>();
        this.states.add(initialState);
        this.size++;
    }

    private State findState(String id){
        for (State state: this.states){
            if (id.equals(state.getId())) return state;
        }

        return null;
    }

    public void connect(String from, String to, String[] label){
        State src = findState(from);
        if (src == null){
            System.out.println("Source index not found " + from);
            return;
        }

        State dest = findState(to);
        if (dest == null) {
            dest = new State(to);
            this.states.add(dest);
        }

        src.createEdge(dest, label);
    }

    public void display(){
        System.out.print("-> ");
        for(State state: this.states){
            for (Edge edge: state.edges){
                System.out.println(edge.toString());
            }
        }
    }

    public static void main(String[] args){
        Automata m = new Automata("q0");
        m.connect("q0", "q1", new String[]{"0"});
        m.connect("q0", "q1", new String[]{"1"});
        m.connect("q0", "q2", new String[]{"1"});
        m.connect("q1", "q2", new String[]{"1"});
        m.display();
    }
}