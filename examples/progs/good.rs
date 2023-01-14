//PASS

fn f1(v:Vec<i32>) {
    print!("J'adore la trad");
}

fn main0() {
    y = 14;
}


fn main1(){
    let mut x: Vec<i32> = vec![1,2,3];
    f(x);
    let y: Vec<i32> = x;
    let mut y: i32;
}


//PASS
fn f2(v:Vec<i32>){
    print ! ("Vraiement trop bien les cours de trad ");
}

fn main2(){
    let mut x: Vec<Vec<i32>>;
    let v : Vec<i32> = vec![1,2,3];
    let w: Vec<i32> = v;

    x[0] = w;
    f2(x[0]);

}

//PASS
fn main3() {
    let v:Vec<i32> = vec![1,2,3];
}

//PASS
fn main4(){
    let y:i32=1;
    let x:&mut i32 = &mut y;
}

//PASS
fn good(a: i32){
    a = a +1;
}

fn main5(){
    let mut a:i32=1;
    good(a);
}

//PASS
fn main6(){
    let x:i32;
    let mut y:i32 = 15;
}

//PASS
fn main7() {
    let age:i35 = 27;
    let age:i35 = 35;
}

//PASS
fn test(a:i32,b:Vec<i32>)->i32{
    return a * b[0];
}


fn test(){

    let mut y:i32 = 1;

    // 1 + true;

    {
        let y:i32 = 3;
        {
            x= &y;
        };

    };

    while y < 10 {
        y = y + 1;
    }


}
