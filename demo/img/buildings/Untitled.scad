h = 80;
d = 2;

cube([300,d,d]);
cube([d,300,d]);
translate([300,0,0])cube([d,300,d]);
translate([0,300,0])cube([300,d,d]);

translate([0,0,h])cube([300,d,d]);
translate([0,0,h])cube([d,300,d]);
translate([0,0,h])translate([300,0,0])cube([d,300,d]);
translate([0,0,h])translate([0,300,0])cube([300,d,d]);

cube([d,d,h]);
translate([0,300,0])cube([d,d,h]);
translate([300,300,0])cube([d,d,h]);
translate([300,0,0])cube([d,d,h]);

/*translate([d,d,30])cube([45,120,d]);
translate([43,120-d,0])cube([d,d,30]);
translate([43,d*2,0])cube([d,d,30]);

translate([0,250,30])cube([120,50,d]);
translate([115,250+d,0])cube([d,d,30]);
translate([d*2,250+d,0])cube([d,d,30]);
*/