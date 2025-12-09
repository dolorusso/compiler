(module
	(import "console" "print_str" (func $print_str (param i32)))
	(import "console" "print_num" (func $print_num (param i32)))
	(global $P10.X (mut i32) (i32.const 0))
	(global $P10.F1.Y (mut i32) (i32.const 0))
	(global $_aux1i (mut i32) (i32.const 0))
	(global $_aux2i (mut i32) (i32.const 0))
	(global $_auxiRes (mut i64) (i64.const 0))
	(global $_aux1f (mut f32) (f32.const 0))
	(global $_aux2f (mut f32) (f32.const 0))
	(global $activa_P10.F1 (mut i32) (i32.const 0))
	(global $activa_P10 (mut i32) (i32.const 0))
	(memory (export "mem") 1)
	(data (i32.const 0) " iteracion 2 \00")
	(data (i32.const 14) " iteracion 1 \00")
	(data (i32.const 28) " ----- \00")
	(data (i32.const 36) " llegue con \00")
	(data (i32.const 49) "[Runtime Error] Recursion detectada.\00")
	(func $P10.F1 (result i32)
		(block $endif_0
			(block $else_0
				global.get $P10.X
				i32.const 1
				i32.ge_s
				i32.eqz
				br_if $else_0
				i32.const 1
				global.set $P10.F1.Y
				(block $rec_P10.F1
					global.get $activa_P10.F1
					i32.eqz
					br_if $rec_P10.F1
					i32.const 49
					call $print_str
					unreachable
				)
				i32.const 1
				global.set $activa_P10.F1
				call $P10.F1
				i32.const 0
				global.set $activa_P10.F1
				drop
			)
		br $endif_0
		)
		global.get $P10.X
		i32.const 1
		i32.add
		global.set $P10.X
		i32.const 36
		call $print_str
		global.get $P10.X
		call $print_num
		i32.const 0
		return
		i32.const 0
		global.set $activa_P10.F1
	)
	(func $P10
		i32.const 0
		global.set $P10.X
		i32.const 14
		call $print_str
		i32.const -1
		global.set $P10.F1.Y
		i32.const 1
		global.set $activa_P10.F1
		call $P10.F1
		i32.const 0
		global.set $activa_P10.F1
		drop
		i32.const 28
		call $print_str
		i32.const 0
		call $print_str
		i32.const -1
		global.set $P10.F1.Y
		i32.const 1
		global.set $activa_P10.F1
		call $P10.F1
		i32.const 0
		global.set $activa_P10.F1
		drop
		i32.const 0
		global.set $activa_P10
	)
	(export "main" (func $P10))
)
