variable "project_id" {
  description = "project id to deploy this resource into"
}

variable "region" {
  description = "region to deploy this resource into"
}

variable "credentials" {
    description = "path to credentials file"
}

provider "google" {
  project     = var.project_id
  credentials = file(var.credentials)
  region      = var.region
}

# vpc
resource "google_compute_network" "vpc" {
  name                    = "${var.project_id}-vpc"
  auto_create_subnetworks = false
}

#subnet
resource "google_compute_subnetwork" "subnet" {
  name          = "${var.project_id}-subnet"
  region        = var.region
  network       = google_compute_network.vpc.name
  ip_cidr_range = "10.10.0.0/24"
}