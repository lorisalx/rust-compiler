fn main(){
  let x:&mut i32 = y;
}

fn test(a:i32,b:Vec<i32>)->i32{
  return 2*b[34];
}

fn good(a: i32){
  a = a +1;
}

fn main(){
  let mut a:i32=1;
  good(a);
}
