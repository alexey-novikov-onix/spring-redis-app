Vagrant.configure(2) do |config|
  config.vm.box = 'williamyeh/ubuntu-trusty64-docker'

  config.vm.network :private_network, ip: '192.168.33.10'
  config.vm.network :forwarded_port, guest: 6379, host: 6379
  config.vm.network :forwarded_port, guest: 5000, host: 5000

  config.ssh.insert_key = false
  config.ssh.forward_agent = true

  config.vm.synced_folder "./", "/src"

  config.vm.provider "virtualbox" do |vb|
    # Customize the amount of memory on the VM:
    vb.memory = 2048
    vb.cpus = 2
    vb.auto_nat_dns_proxy = false
    vb.customize ['modifyvm', :id, '--natdnsproxy1', 'off']
    vb.customize ['modifyvm', :id, '--natdnshostresolver1', 'on']
  end
end