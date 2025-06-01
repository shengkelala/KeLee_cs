package work1;

import edu.princeton.cs.algs4.*;
public class Percolation {
    private boolean[] state;
    private int sidelength;
    private WeightedQuickUnionUF wq;
    private QuickFindUF qf;
    private int choice;
    public Percolation(int N,int choice) {
        sidelength=N;
        this.choice=choice;
        state=new boolean[N*N+2];
         if(choice==2){
            wq= new WeightedQuickUnionUF(N*N+2);
         }
         if(choice==1){
             qf=new QuickFindUF(N*N+2);
         }
         for(int i=0;i<N*N+2;i++){
             state[i]=false;
         }
         state[0]=true;
         state[N*N+1]=true;
    }// create N-by-N grid, with all sites blocked
    public void open(int i, int j){
         if(i<1||i>sidelength||j<1||j>sidelength){
             throw new IllegalArgumentException("have  index  is not between 1 and " + (sidelength));
         }
         state[(i-1)*sidelength+j]=true;
         if(choice==2){
         if(i==1){
             wq.union(0,j);
         }
         if(i==sidelength){
             wq.union(sidelength*sidelength+1,(i-1)*sidelength+j);
         }
         //连通左右上下的open节点
        if(j!=1&&state[(i-1)*sidelength+j-1]){
            wq.union((i-1)*sidelength+j-1,(i-1)*sidelength+j);
        }
        if(j!=sidelength&&state[(i-1)*sidelength+j+1]){
            wq.union((i-1)*sidelength+j,(i-1)*sidelength+j+1);
        }
        if(i!=1&&state[(i-2)*sidelength+j]){
            wq.union((i-2)*sidelength+j,(i-1)*sidelength+j);
        }
        if(i!=sidelength&&state[i*sidelength+j]){
            wq.union((i-1)*sidelength+j,i*sidelength+j);
        }}
         else if(choice==1){
             if(i==1){
                 qf.union(0,j);
             }
             if(i==sidelength){
                 qf.union(sidelength*sidelength+1,(i-1)*sidelength+j);
             }
             //连通左右上下的open节点
             if(j!=1&&state[(i-1)*sidelength+j-1]){
                 qf.union((i-1)*sidelength+j-1,(i-1)*sidelength+j);
             }
             if(j!=sidelength&&state[(i-1)*sidelength+j+1]){
                 qf.union((i-1)*sidelength+j,(i-1)*sidelength+j+1);
             }
             if(i!=1&&state[(i-2)*sidelength+j]){
                 qf.union((i-2)*sidelength+j,(i-1)*sidelength+j);
             }
             if(i!=sidelength&&state[i*sidelength+j]){
                 qf.union((i-1)*sidelength+j,i*sidelength+j);
             }
         }
    } // open site (row i, column j) if it is not already
    public boolean isOpen(int i, int j){
        if(i<1||i>sidelength||j<1||j>sidelength){
            throw new IllegalArgumentException("have  index  is not between 1 and " + (sidelength));
        }
        return state[(i-1)*sidelength+j];
    } // is site (row i, column j) open?
    public boolean isFull(int i, int j) {
        if (choice == 2) {
            return wq.connected(0, (i - 1) * sidelength + j);
        }
         else{
            return qf.connected(0, (i - 1) * sidelength + j);
        }
    }// is site (row i, column j) full?
    public boolean percolates() {
        if(choice==2){
            return wq.connected(0, sidelength*sidelength+1);
        }
        else{
            return qf.connected(0, sidelength*sidelength+1);
        }
    }// does the system percolate?

}
