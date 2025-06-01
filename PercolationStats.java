package work1;



import java.util.Scanner;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private int length;
    private int t;
    private double[] experiments;
    private double means,std,conlo,conhi;
    public PercolationStats(int N, int T,int choice){
        if(N <= 0 || T <= 0)
            throw new IllegalArgumentException("Illegal Argument Exception");
        length=N;
        if(length == 1){
            means = 1;            //渗透阈值的平均值
            std = Double.NaN; //渗透阈值的样本标准差
            conlo = Double.NaN;  //95%置信区间下限
            conhi = Double.NaN;  //95%置信区间上限
        }
        else{
            t = T;
            experiments = new double[t];
            for(int i = 0; i < T; i++){
                Percolation percolation=new Percolation(N,choice);
                double count=0;
                while(!percolation.percolates()){
                    int row=StdRandom.uniform(0,N)+1;
                    int col=StdRandom.uniform(0,N)+1;
                    if(!percolation.isOpen(row,col)){
                        count++;
                    }
                    percolation.open(row,col);
                }
                experiments[i]=count*1.0/(N*N);
            }
            means = StdStats.mean(experiments);      //渗透阈值的样本均值
            std = StdStats.stddev(experiments);  //渗透阈值的样本标准差
            conlo = means - (1.96 * std) / Math.sqrt(T); //95％置信区间下限
            conhi = means + (1.96 * std) / Math.sqrt(T); //95％置信区间上限
        }


    } //perform T independent computational experiments on an NxN grid
    public double mean(){
        return means;
    } // sample mean of percolation threshold
    public double stddev() {
        return std;
    }// sample standard deviation of percolation threshold
    public double confidenceLo() {
        return conlo;
    }// returns lower bound of the 95% confidence interval
    public double confidenceHi() {
        return conhi;
    }// returns upper bound of the 95% confidence interval
    public static void main(String[] args){
         Scanner sc=new Scanner(System.in);
         System.out.print("Enter the sidelength: ");
         int n=sc.nextInt();
         System.out.print("Enter the traversal times: ");
         int t=sc.nextInt();
         System.out.println("Enter the method:1--quik find  2--weighted union");
         int choice=sc.nextInt();
         sc.close();
         long start_time=System.nanoTime();
         PercolationStats percolationstates=new PercolationStats(n,t,choice);
        long consumingtime=System.nanoTime()-start_time;
        System.out.println("--------------------------------------------------------");
        if(choice==1) {
            System.out.println("The algorithm currently used is：Quick_Find");
        }
        else if(choice==2) {
            System.out.println("The algorithm currently used is：Weighted Quick Union");
        }
        System.out.println("Creating PercolationStats( "+n+" , "+t+" )");
        System.out.println("mean:\t\t\t\t= " + percolationstates.mean());
        System.out.println("stddev:\t\t\t\t= " + percolationstates.stddev());
        System.out.println("confidence_Low:\t\t= " + percolationstates.confidenceLo());
        System.out.println("confidence_High:\t= " + percolationstates.confidenceHi());
        System.out.println("The cost of the time is "+consumingtime*1.0/1000000+"ms");
    } // test client, described below
}
