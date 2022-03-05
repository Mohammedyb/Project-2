terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.11.0"
    }
  }
}

// configure the terraform google provider
provider "google" {
  credentials = file(var.credentials_file)
  project     = var.project
  region      = var.region
}


// Terraform plugin for creating random ids
resource "random_id" "instance_id" {
  byte_length = 8
}

resource "random_id" "vpc_id" {
  byte_length = 8
}

// create a vpc for our resources
resource "google_compute_network" "vpc_network" {
  name = "${var.vpc_name}${random_id.vpc_id.hex}"
}

// A single Compute Engine instance
resource "google_compute_instance" "terraformed_instance" {
  name         = "${var.instance_name_base}${random_id.instance_id.hex}"
  machine_type = var.machine_type
  zone         = var.zone
  tags         = var.instance_tags

  boot_disk {
    initialize_params {
      image = var.boot_image
    }
  }

  network_interface {
    network = google_compute_network.vpc_network.name

    access_config {
      // Include this section to give the VM an external ip address
    }
  }

  metadata = {
    "ssh-keys" = var.include_ssh_access ? "${var.ssh_username}:${file(var.ssh-key_file)}" : null
    "startup-script" = var.include_metadatascript ? file(var.metadata_script) : null
  }
}

resource "google_compute_firewall" "terraformed_firewall" {
  # name    = "http-8080-ingress"
  # network = "default"
  # source_ranges = ["0.0.0.0/0"]
  # target_tags = ["http-server"]

  # allow {
  #   protocol = "tcp"
  #   ports    = ["8080"]
  # }

  for_each      = var.firewall_rules
  name          = each.key
  network       = google_compute_network.vpc_network.name
  source_ranges = each.value.source_ranges
  target_tags   = each.value.target_tags

  allow {
    protocol = each.value.allow.protocol
    ports    = each.value.allow.ports
  }

}


output "ip" {
  value = google_compute_instance.terraformed_instance.network_interface.0.access_config.0.nat_ip
}