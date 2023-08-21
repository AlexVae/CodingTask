import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        
        int[] list = new int[X + 1];
        segmentTree = new int[4 * (X + 1)];
        
        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            
            update(1, 1, X, i, j, k);
        }
        
        int max = query(1, 1, X, 1, X);
        System.out.println(max);
    }
    
    static void update(int node, int start, int end, int left, int right, int val) {
        if (left > end || right < start) {
            return;
        }
        
        if (left <= start && end <= right) {
            segmentTree[node] += val;
            return;
        }
        
        int mid = (start + end) / 2;
        update(2 * node, start, mid, left, right, val);
        update(2 * node + 1, mid + 1, end, left, right, val);
        
        segmentTree[node] = Math.max(segmentTree[2 * node], segmentTree[2 * node + 1]);
    }
    
    static int query(int node, int start, int end, int left, int right) {
        if (left > end || right < start) {
            return 0;
        }
        
        if (left <= start && end <= right) {
            return segmentTree[node];
        }
        
        int mid = (start + end) / 2;
        int leftMax = query(2 * node, start, mid, left, right);
        int rightMax = query(2 * node + 1, mid + 1, end, left, right);
        
        return Math.max(leftMax, rightMax);
    }
}

/*
I chose the approach of using a Segment Tree to improve efficiency because it allows for efficient range updates and queries, particularly when dealing with a large number of operations and a relatively large input size. The Segment Tree approach has a logarithmic time complexity for both updates and queries, which is significantly more efficient than the linear time complexity of iterating through the entire array for each query as in the initial approach.

Other possible solutions and trade-offs include:

Prefix Sum Array: Instead of using a Segment Tree, you could use a prefix sum array to keep track of cumulative updates. While this would allow efficient querying of ranges, it doesn't handle range updates as efficiently as a Segment Tree. Range updates would still take linear time complexity in the worst case.

Fenwick Tree (Binary Indexed Tree): A Fenwick Tree is another data structure that supports efficient prefix sum updates and queries, but it might require more complex implementation compared to the Segment Tree. Depending on the problem constraints, a Fenwick Tree could be a good alternative.

Lazy Propagation Segment Tree: This is an extension of the regular Segment Tree that optimizes the updates by postponing them until necessary. This could lead to faster updates and slightly more complex code compared to the non-lazy approach.

Sparse Table: A Sparse Table is another data structure that can be used to efficiently perform range queries on static data. However, it's not as well-suited for range updates, so it might not be the best choice for this particular problem.

Segment Tree with Lazy Propagation and Coordinate Compression: If the range of indices (i, j) is large and sparse, you might consider combining lazy propagation in the Segment Tree with coordinate compression. This involves mapping the original indices to a smaller range and using lazy propagation to handle updates.

Parallel Processing: If you have multiple cores available, you could parallelize the operations to perform updates and queries in parallel. However, this might introduce additional complexity and synchronization overhead.
 */