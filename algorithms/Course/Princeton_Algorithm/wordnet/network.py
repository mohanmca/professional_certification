import networkx as nx
import matplotlib.pyplot as plt

def main():
    file_name = ["digraph1.txt", "hypernyms15Tree.txt"]
    f = open(file_name[1], 'r')
    content = f.read()
    graph = content.split("\n")
    graph.pop()
    g = nx.DiGraph()
    g.add_nodes_from([i for i in graph[0]])
    for edge in graph[2:]:
        print(edge)
        v, w = edge.strip().split()
        g.add_edge(v, w)
    nx.draw(g,with_labels=True)
    plt.draw()
    plt.show()

if __name__ == "__main__":
    main()
