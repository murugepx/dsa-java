public class SocialNetwork {
  private int[] id;
  private int[] sz;

    public SocialNetwork(int n) {
        id = new int[n];
        sz = new int[n];
        for(int i=0; i< n; i++) {
            id[i]=i;
            sz[i]=1;
        }    
    }

    private int root(int i) {
        while(i != id[i]) {
            id[i]=id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j= root(q);
        if(i ==j) return;
        if(sz[i] < sz[j]) {
            id[i]=j;
            sz[j] += sz[i];
        }
        else {
            id[j]=i;
            sz[i] += sz[j];
        }        
    }

    public boolean allConnected(int n) {
        int r=root(0);
        for(int i=1; i< n; i++) {
            if(root(i) != r) {
                return false;
            }
        } 
        return true;
    }

    public static void main(String[] args) {
        // Get number of members => n
        SocialNetwork uf = new SocialNetwork(n);
        // get number of timestamps in log file => m
        for(int i=0;i<m;i++) {
            // get pairs of members from log entry => p, q
            uf.union(p, q);
            if(uf.allConnected(n)) {
                // return time from log entry i
                break;
            }            
        }
    }
}
