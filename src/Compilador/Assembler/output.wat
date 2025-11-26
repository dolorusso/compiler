(module
	(import "console" "print_str" (func $print_str (param i32)))
	(import "console" "print_num" (func $print_num (param i32)))
	(global $P15.X (mut i32) (i32.const 0))
	(global $P15.Y (mut i32) (i32.const 0))
	(global $P15.F1.A (mut i32) (i32.const 0))
	(global $P15.F1.B (mut i32) (i32.const 0))
	(global $_aux1i (mut i32) (i32.const 0))
	(global $_aux2i (mut i32) (i32.const 0))
	(global $_auxiRes (mut i64) (i64.const 0))
	(global $_aux1f (mut f32) (f32.const 0))
	(global $_aux2f (mut f32) (f32.const 0))
	(global $activa_P15 (mut i32) (i32.const 0))
	(global $activa_P15.F1 (mut i32) (i32.const 0))
	(memory (export "mem") 1)
	(data (i32.const 0) "X \00")
	(data (i32.const 3) "Y \00")
	(data (i32.const 6) " --- \00")
	(data (i32.const 12) " --- DESPUES:  \00")
	(data (i32.const 28) "[Runtime Error] Recursion detectada.\00")
	(func $P15
		i32.const 0
		global.set $P15.X
		i32.const 0
		global.set $P15.Y
		i32.const 0
		call $print_str
		global.get $P15.X
		call $print_num
		i32.const 6
		call $print_str
		i32.const 3
		call $print_str
		global.get $P15.Y
		call $print_num
		i32.const 12
		call $print_str
		global.get $P15.X
		global.set $P15.F1.A
		i32.const 1
		global.set $activa_P15.F1
		call $P15.F1
		i32.const 0
		global.set $activa_P15.F1
		global.get $P15.F1.B
		global.set $P15.Y
		drop
		i32.const 0
		call $print_str
		global.get $P15.X
		call $print_num
		i32.const 6
		call $print_str
		i32.const 3
		call $print_str
		global.get $P15.Y
		call $print_num
		i32.const 0
		global.set $activa_P15
	)
	(func $P15.F1 (result i32)
		i32.const 10
		global.set $P15.F1.A
		i32.const 10
		global.set $P15.F1.B
		i32.const 0
		return
		i32.const 0
		global.set $activa_P15.F1
	)
	(export "main" (func $P15))
)
