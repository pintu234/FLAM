#include <iostream>
#include <vector>
using namespace std;

bool dfs(int node, vector<vector<int>> &adj, vector<bool> &visited, vector<bool> &recStack) {
    visited[node] = true;
    recStack[node] = true;

    for (int neighbor : adj[node]) {
        if (!visited[neighbor]) {
            if (dfs(neighbor, adj, visited, recStack))
                return true;
        } else if (recStack[neighbor]) {
            return true; 
        }
    }

    recStack[node] = false; 
    return false;
}

bool hasCircularDependency(int V, vector<vector<int>> &adj) {
    vector<bool> visited(V, false);
    vector<bool> recStack(V, false);

    for (int i = 0; i < V; ++i) {
        if (!visited[i]) {
            if (dfs(i, adj, visited, recStack))
                return true;
        }
    }

    return false;
}

int main() {
    int V;cin>>V;
    vector<vector<int>> adj(V);
    
    int numEdges; cin >> numEdges;
    for (int i = 0; i < numEdges; ++i) {
        int u, v;
        cin >> u >> v;
        adj[u].push_back(v);
    }
    

    if (hasCircularDependency(V, adj))
        cout << "Cycle detected in the directed graph\n";
    else
        cout << "No cycle in the directed graph\n";

    return 0;
}
