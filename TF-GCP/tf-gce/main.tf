terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.11.0"
    }
  }
}

provider "google" {
  credentials = file("spherical-gate-creds.json")
  project     = "windy-album-339219"
  region      = "us-central1-a"
}

# This plugin generates semi random ids
resource "random_id" "instance_id" {
  byte_length = 8
}

# VPC (Virtual Private Cloud) network inside google cloud network
resource "google_compute_network" "vpc_network" {
  name = "terraformed-network"
}

# Single Compute Engine Instance
resource "google_compute_instance" "instance" {
  name         = "terraform-instance-${random_id.instance_id.hex}"
  machine_type = "e2-medium"
  zone         = "us-central1-a"
  tags         = ["terraformed-instance"]

  boot_disk {
    initialize_params {
      image = "ubuntu-1804-lts"
    }
  }

  network_interface {
    network = google_compute_network.vpc_network.name

    access_config {
      # Include and leave empty to give vm an external ip address
    }
  }

  metadata_startup_script = file("metadata_script.sh")

  metadata = {
    "ssh-keys" = "mohammed.bubshait:${file("~/.ssh/id_ed25519.pub")}"
  }
}

# Configure firewall exception for VM 8080
resource "google_compute_firewall" "terraformed_firewall_exception" {
  name          = "http-8080-ingress"
  network       = google_compute_network.vpc_network.name
  source_ranges = ["0.0.0.0/0"]
  target_tags   = ["terraformed-instance"]

  allow {
    protocol = "tcp"
    ports    = ["8080"]
  }
}

# Configure firewall exception for VM 22
resource "google_compute_firewall" "terraformed_firewall_exception_ssh" {
  name          = "http-22-ingress"
  network       = google_compute_network.vpc_network.name
  source_ranges = ["0.0.0.0/0"]

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }
}

output "ip" {
  value = google_compute_instance.instance.network_interface.0.access_config.0.nat_ip
}