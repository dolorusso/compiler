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
	(global $activa_A (mut i32) (i32.const 0))
	(memory (export "mem") 1)
	(data (i32.const 0) "[Runtime Error] Overflow al multiplicar.\00")
	(func $A
		i32.const 1
		global.set $activa_A
		i32.const 9
		global.set $_aux2i
		i32.const 10
		global.set $_aux1i
		call $integer-overflow-checker
		global.get $_aux1i
		global.get $_aux2i
		i32.mul
		i32.const 3
		global.set $_aux2i
		global.set $_aux1i
		call $integer-overflow-checker
		global.get $_aux1i
		global.get $_aux2i
		i32.mul
		global.set $A.X
		global.get $A.X
		call $print_num
		i32.const 0
		global.set $activa_A
	)
	(func $integer-overflow-checker
		(block $fin
			(block $overflow
				global.get $_aux1i
				i64.extend_i32_s
				global.get $_aux2i
				i64.extend_i32_s
				i64.mul
				global.set $_auxiRes
				global.get $_auxiRes
				i64.const 2147483647
				i64.gt_s
				br_if $overflow
				global.get $_auxiRes
				i64.const -2147483648
				i64.lt_s
				br_if $overflow
				br $fin
			)
				i32.const 0
				call print_str
				unreachable
			)
		)
		(export "main" (func $A))
	)
