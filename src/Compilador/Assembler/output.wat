(module
	(import "console" "print_str" (func $print_str (param i32)))
	(import "console" "print_num" (func $print_num (param i32)))
	(global $P11.X (mut i32) (i32.const 0))
	(global $_aux1i (mut i32) (i32.const 0))
	(global $_aux2i (mut i32) (i32.const 0))
	(global $_auxiRes (mut i64) (i64.const 0))
	(global $_aux1f (mut f32) (f32.const 0))
	(global $_aux2f (mut f32) (f32.const 0))
	(global $activa_P11 (mut i32) (i32.const 0))
	(memory (export "mem") 1)
	(data (i32.const 0) "funciona: \00")
	(data (i32.const 11) "aca no llega: \00")
	(data (i32.const 26) "[Runtime Error] Truncamiento genera perdida de informacion.\00")
	(func $P11
		f32.const 9.5
		f32.const 0.5
		f32.add
		global.set $_aux1f
		global.get $_aux1f
		i32.trunc_f32_s
		global.set $_aux1i
		call $trunc-checker
		global.get $_aux1i
		global.set $P11.X
		i32.const 0
		call $print_str
		global.get $P11.X
		call $print_num
		f32.const 9.5
		f32.const 0.6
		f32.add
		global.set $_aux1f
		global.get $_aux1f
		i32.trunc_f32_s
		global.set $_aux1i
		call $trunc-checker
		global.get $_aux1i
		global.set $P11.X
		i32.const 11
		call $print_str
		global.get $P11.X
		call $print_num
		i32.const 0
		global.set $activa_P11
	)
	(func $trunc-checker
		(block $endif_truncar
			(block $else_truncar
				global.get $_aux1i
				f32.convert_i32_s
				global.get $_aux1f
				f32.eq
				br_if $else_truncar
				i32.const 26
				call $print_str
				unreachable
			)
		br $endif_truncar
		)
	)
	(export "main" (func $P11))
)
