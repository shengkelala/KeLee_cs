import subprocess

# 初始 MCR2_value
MCR2_value = 0.02  # 可以根据需要修改初始值

# 执行5次，每次将 MCR2_value 除以 2
for i in range(1, 6):  # 5次循环
    # 打印当前 MCR2_value 和循环次数
    print(f"Running loop {i}, MCR2_value: {MCR2_value}")

    # 构建命令行并执行训练脚本
    command = [
        'python', 'train_clip_adaptive_lora.py',
        '--config', 'configs/Retrieval_rsicd_geo.yaml',
        '--max_epoch', '80',
        '--batch_size_train', '200',
        '--output_dir', f'./checkpoints/GeoRSclip_Ret2/test_{MCR2_value:.6f}MCR2_rsicd_0.15_0.15_epoch80_lr1e-4_shareA32_64_soft',
        '--coefficient_triplet1', '0.25',
        '--coefficient_triplet2', '0.15',
        '--coefficient_contr1', '0.25',
        '--coefficient_contr2', '0.15',
        '--best_performance', '40.15',
        '--lr', '1e-4',
        '--model_module', 'open_clip_sharepara_soft',
        '--mcr2_value', str(MCR2_value)  # 将 MCR2_value 作为参数传递给训练脚本
    ]

    # 使用 subprocess 执行命令
    subprocess.run(command)

    # 将 MCR2_value 除以 2，为下一次循环准备
    MCR2_value /= 2
