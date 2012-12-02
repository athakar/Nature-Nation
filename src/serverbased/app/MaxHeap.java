package serverbased.app;

public class MaxHeap{

	Obj [] o;
	int N;
	int size;
	
	public MaxHeap(int n){
		o = new Obj[n+1];
		size =0;
		N = n;
	}
	
	public class Obj{
		double k;
		Entry v;
		
		public Obj(double k, Entry v){
			this.k = k;
			this.v = v;
		}
	}
	
	
void push(double k, Entry v) {
		Obj temp = new Obj(k,v);
		o[++size] = temp;
		swim(size);
	}

Obj pop() {
	Obj del = o[1];

	Obj temp = o[1];
	o[1] = o[size];
	o[size--] = temp;

	sink(1);
	return del;
}

boolean isLess(int i, int j){
	return o[i].k < o[j].k;
	}
	
void swim(int index){
	Obj temp;
	while(index > 1 && isLess(index/2,index)){
			temp = o[index];
			o[index] = o[index/2];
			o[index/2] = temp;

			index = index/2;
		}
	}

void sink(int index){
	int i;
	Obj temp;
	while(2*index <= size){
		i = 2*index;
		if(i < size && isLess(i, i+1))
			i++;
		if(!isLess(index,i))
			break;	
		temp = o[index];
		o[index] = o[i];
		o[i] = temp;

		index = i;
		}
	}
	
}
