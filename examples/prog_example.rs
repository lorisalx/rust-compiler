// This is a comment
fn main() {
    print!("Hello World!");
}

// Structure declaration
struct Point {
    x: i32,
    y: i32,
}

// Function declaration
fn testFunction(a:i32, b:i64 ) -> i32 {
    // Variables declaration
    let mut s:i32 = 5;

    // Bloc declaration
    {
        let mut xInBlock:i32 = 5;
        // Bloc in a bloc (TDS)
        {
            let mut xInBlockInBlock:i32 = 10;
        };
    };

    return s;
}