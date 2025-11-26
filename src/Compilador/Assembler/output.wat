(module
	(import "console" "print_str" (func $print_str (param i32)))
	(import "console" "print_num" (func $print_num (param i32)))
	(global $A.X (mut i32) (i32.const 0))
	(global $A.Y (mut i32) (i32.const 0))
	(global $_aux1i (mut i32) (i32.const 0))
	(global $_aux2i (mut i32) (i32.const 0))
	(global $_auxiRes (mut i64) (i64.const 0))
	(global $_aux1f (mut f32) (f32.const 0))
	(global $_aux2f (mut f32) (f32.const 0))
	(memory (export "mem") 1)
	(data (i32.const 0) "A1\00")
	(data (i32.const 3) "B\00")
	(data (i32.const 5) "A3\00")
	(data (i32.const 8) "A2\00")
	(data (i32.const 11) "A4\00")
	(func $A
		i32.const 2
		global.set $A.X
		i32.const 7
		global.set $A.Y
		(block $endif_0
			(block $else_0
				global.get $A.X
				i32.const 1
				i32.gt_s
				i32.eqz
				br_if $else_0
				i32.const 0
				call $print_str
				(block $endif_1
					(block $else_1
						global.get $A.Y
						i32.const 5
						i32.lt_s
						i32.eqz
						br_if $else_1
						i32.const 8
						call $print_str
						br $endif_1
					)
					i32.const 5
					call $print_str
					(block $endif_2
						(block $else_2
							global.get $A.Y
							i32.const 7
							i32.eq
							i32.eqz
							br_if $else_2
							i32.const 11
							call $print_str
						)
					br $endif_2
					)
				)
				br $endif_0
			)
			i32.const 3
			call $print_str
		)
	)
	(export "main" (func $A))
)
