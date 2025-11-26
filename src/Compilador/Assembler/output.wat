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
	(data (i32.const 0) "aaa\00")
	(data (i32.const 4) "[Runtime Error] Truncamiento genera perdida de informacion.\00")
	(func $A
		i32.const 2
		global.set $A.X
		f32.const 2.0
		f32.const 3.5
		f32.add
		global.set $_aux1f
		global.get $_aux1f
		i32.trunc_f32_s
		global.set $_aux1i
		call $trunc-checker
		global.get $_aux1i
		global.set $A.Y
		(block $endif_0
			(block $else_0
				i32.const 2
				i32.const 3
				i32.gt_s
				i32.eqz
				br_if $else_0
				i32.const 0
				call $print_str
			)
		br $endif_0
		)
	)
	(func $trunc-checker
		(block $endif_truncar
			(block $else_truncar
				global.get $_aux1i
				f32.convert_i32_s
				global.get $_aux1f
				f32.ne
				br_if $else_truncar
				i32.const 0
				call $print_str
				unreachable
			)
		br $endif_truncar
		)
	)
	(export "main" (func $A))
)
